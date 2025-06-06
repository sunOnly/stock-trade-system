package com.stock.trade.order.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.stock.trade.common.core.domain.PageResult;
import com.stock.trade.common.core.exception.ServiceException;
import com.stock.trade.order.controller.request.OrderCreateReqVO;
import com.stock.trade.order.controller.request.OrderPageReqVO;
import com.stock.trade.order.controller.request.OrderUpdateReqVO;
import com.stock.trade.order.convert.OrderConvert;
import com.stock.trade.order.dal.dataobject.OrderDO;
import com.stock.trade.order.dal.dataobject.OrderItemDO;
import com.stock.trade.order.dal.dataobject.OrderLogDO;
import com.stock.trade.order.dal.mysql.OrderMapper;
import com.stock.trade.order.enums.ErrorCodeConstants;
import com.stock.trade.order.enums.OrderStatusEnum;
import com.stock.trade.order.service.OrderItemService;
import com.stock.trade.order.service.OrderLogService;
import com.stock.trade.order.service.OrderService;
import com.stock.trade.order.mq.message.OrderMessage;
import com.stock.trade.order.mq.producer.OrderProducer;
import com.stock.trade.order.mq.message.TradeOrderReturnMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static com.stock.trade.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * 订单 Service 实现类
 *
 * @author xxx
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrderItemService orderItemService;

    @Resource
    private OrderLogService orderLogService;

    @Resource
    private OrderProducer orderProducer;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createOrder(OrderCreateReqVO createReqVO) {
        // 1. 校验订单参数
        validateCreateOrder(createReqVO);

        // 2. 创建订单
        OrderDO order = OrderConvert.INSTANCE.convert(createReqVO);
        order.setStatus(OrderStatusEnum.PENDING_NEW.getStatus());
        order.setOrderTime(LocalDateTime.now());
        order.setFilledQuantity(0);
        orderMapper.insert(order);

        // 3. 创建订单日志
        OrderLogDO orderLog = new OrderLogDO()
                .setOrderId(order.getId())
                .setBeforeStatus(null)
                .setAfterStatus(order.getStatus())
                .setContent("创建订单")
                .setOperationTime(LocalDateTime.now())
                .setOperatorUserId(order.getUserId());
        orderLogService.createOrderLog(orderLog);

        // 发送订单状态变更消息
        orderProducer.sendOrderMessage(new OrderMessage().setOrderId(order.getId()).setUserId(order.getUserId()).setStatus(order.getStatus()));

        return order.getId();
    }

    private void validateCreateOrder(OrderCreateReqVO createReqVO) {
        // 1. 校验价格
        if (createReqVO.getPrice() != null && createReqVO.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw exception(ErrorCodeConstants.ORDER_PRICE_INVALID);
        }
        // 2. 校验数量
        if (createReqVO.getQuantity() <= 0) {
            throw exception(ErrorCodeConstants.ORDER_QUANTITY_INVALID);
        }
        // 3. 校验订单类型
        if (!OrderTypeEnum.ARRAYS.contains(createReqVO.getType())) {
            throw exception(ErrorCodeConstants.ORDER_TYPE_INVALID);
        }
        // 4. 校验订单方向
        if (!OrderDirectionEnum.ARRAYS.contains(createReqVO.getDirection())) {
            throw exception(ErrorCodeConstants.ORDER_DIRECTION_INVALID);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrder(OrderUpdateReqVO updateReqVO) {
        // 1. 校验订单存在
        OrderDO order = validateOrderExists(updateReqVO.getId());

        // 2. 校验订单状态是否允许修改
        if (OrderStatusEnum.isFinalStatus(order.getStatus())) {
            throw exception(ErrorCodeConstants.ORDER_STATUS_INVALID);
        }

        // 3. 更新订单
        OrderDO updateObj = OrderConvert.INSTANCE.convert(updateReqVO);
        orderMapper.updateById(updateObj);

        // 4. 创建订单日志
        OrderLogDO orderLog = new OrderLogDO()
                .setOrderId(order.getId())
                .setBeforeStatus(order.getStatus())
                .setAfterStatus(order.getStatus())
                .setContent("更新订单信息")
                .setOperationTime(LocalDateTime.now())
                .setOperatorUserId(order.getUserId());
        orderLogService.createOrderLog(orderLog);

        // 发送订单状态变更消息
        orderProducer.sendOrderMessage(new OrderMessage().setOrderId(order.getId()).setUserId(userId).setStatus(OrderStatusEnum.CANCELED.getStatus()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderStatus(Long id, Integer status, String remark) {
        // 1. 校验订单存在
        OrderDO order = validateOrderExists(id);

        // 2. 更新订单状态
        OrderDO updateObj = new OrderDO()
                .setId(id)
                .setStatus(status)
                .setRemark(remark);
        orderMapper.updateById(updateObj);

        // 3. 创建订单日志
        OrderLogDO orderLog = new OrderLogDO()
                .setOrderId(order.getId())
                .setBeforeStatus(order.getStatus())
                .setAfterStatus(status)
                .setContent("更新订单状态")
                .setOperationTime(LocalDateTime.now())
                .setOperatorUserId(order.getUserId());
        orderLogService.createOrderLog(orderLog);

        // 发送订单状态变更消息
        orderProducer.sendOrderMessage(new OrderMessage().setOrderId(order.getId()).setUserId(userId).setStatus(OrderStatusEnum.CANCELED.getStatus()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelOrder(Long id, Long userId) {
        // 1. 校验订单存在
        OrderDO order = validateOrderExists(id);

        // 2. 校验订单是否属于当前用户
        if (!order.getUserId().equals(userId)) {
            throw exception(ErrorCodeConstants.ORDER_NOT_EXISTS);
        }

        // 3. 校验订单状态是否允许撤销
        if (!OrderStatusEnum.canCancel(order.getStatus())) {
            throw exception(ErrorCodeConstants.ORDER_STATUS_INVALID);
        }

        // 4. 更新订单状态为已撤销
        OrderDO updateObj = new OrderDO()
                .setId(id)
                .setStatus(OrderStatusEnum.CANCELED.getStatus())
                .setRemark("用户主动撤销");
        orderMapper.updateById(updateObj);

        // 5. 创建订单日志
        OrderLogDO orderLog = new OrderLogDO()
                .setOrderId(order.getId())
                .setBeforeStatus(order.getStatus())
                .setAfterStatus(OrderStatusEnum.CANCELED.getStatus())
                .setContent("撤销订单")
                .setOperationTime(LocalDateTime.now())
                .setOperatorUserId(userId);
        orderLogService.createOrderLog(orderLog);

        // 发送订单状态变更消息
        orderProducer.sendOrderMessage(new OrderMessage().setOrderId(order.getId()).setUserId(userId).setStatus(OrderStatusEnum.CANCELED.getStatus()));
    }

    @Override
    public void deleteOrder(Long id) {
        // 校验订单存在
        validateOrderExists(id);
        // 删除订单
        orderMapper.deleteById(id);
    }

    private OrderDO validateOrderExists(Long id) {
        OrderDO order = orderMapper.selectById(id);
        if (order == null) {
            throw exception(ErrorCodeConstants.ORDER_NOT_EXISTS);
        }
        return order;
    }

    @Override
    public OrderDO getOrder(Long id) {
        return orderMapper.selectById(id);
    }

    @Override
    public List<OrderDO> getOrderList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return CollUtil.newArrayList();
        }
        return orderMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<OrderDO> getOrderPage(OrderPageReqVO pageReqVO) {
        // 查询订单分页
        List<OrderDO> list = orderMapper.selectPage(pageReqVO.getPageQuery(),
                pageReqVO.getUserId(), pageReqVO.getStockCode(), pageReqVO.getType(),
                pageReqVO.getDirection(), pageReqVO.getStatus());
        // 查询总数
        Long total = orderMapper.selectCount(pageReqVO.getUserId(), pageReqVO.getStockCode(),
                pageReqVO.getType(), pageReqVO.getDirection(), pageReqVO.getStatus());
        return new PageResult<>(list, total);
    }

    @Override
    public List<OrderDO> getUnfinishedOrdersByUserId(Long userId) {
        return orderMapper.selectList(new LambdaQueryWrapperX<OrderDO>()
                .eq(OrderDO::getUserId, userId)
                .in(OrderDO::getStatus, OrderStatusEnum.PENDING_NEW.getStatus(),
                        OrderStatusEnum.NEW.getStatus(),
                        OrderStatusEnum.PARTIALLY_FILLED.getStatus()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void processOrderReturn(TradeOrderReturnMessage message) {
        log.info("[processOrderReturn][接收到订单成交回报消息：{}]", message);
        processOrderFillInternal(message.getOrderId(), message.getStockCode(), message.getDirection(),
                message.getFillPrice(), message.getFillQuantity(), message.getTradeNo(), true);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void processOrderFill(Long orderId, String stockCode, Integer direction,
                               BigDecimal fillPrice, Integer fillQuantity, String tradeNo) {
        processOrderFillInternal(orderId, stockCode, direction, fillPrice, fillQuantity, tradeNo, false);
    }

    private void processOrderFillInternal(Long orderId, String stockCode, Integer direction,
                                          BigDecimal fillPrice, Integer fillQuantity, String tradeNo, boolean fromMq) {
        // 1. 校验订单存在
        OrderDO order = validateOrderExists(orderId);

        // 2. 校验成交回报参数
        if (!order.getStockCode().equals(stockCode) || !order.getDirection().equals(direction)) {
            log.error("[processOrderFillInternal][订单({})的股票代码({})或方向({})不匹配, fromMq:{}]", orderId, stockCode, direction, fromMq);
            throw exception(ErrorCodeConstants.ORDER_NOT_EXISTS);
        }

        // 3. 创建订单项
        OrderItemDO orderItem = new OrderItemDO()
                .setOrderId(orderId)
                .setTradeNo(tradeNo)
                .setFillPrice(fillPrice)
                .setFillQuantity(fillQuantity)
                .setFillTime(LocalDateTime.now())
                .setCommission(calculateCommission(fillPrice, fillQuantity)); // 计算手续费
        orderItemService.createOrderItem(orderItem);

        // 4. 更新订单成交信息
        Integer newFilledQuantity = order.getFilledQuantity() + fillQuantity;
        BigDecimal newAvgFillPrice = calculateAvgFillPrice(order.getAvgFillPrice(),
                order.getFilledQuantity(), fillPrice, fillQuantity);
        Integer newStatus = newFilledQuantity.equals(order.getQuantity()) ?
                OrderStatusEnum.FILLED.getStatus() : OrderStatusEnum.PARTIALLY_FILLED.getStatus();

        OrderDO updateObj = new OrderDO()
                .setId(orderId)
                .setFilledQuantity(newFilledQuantity)
                .setAvgFillPrice(newAvgFillPrice)
                .setStatus(newStatus);
        orderMapper.updateById(updateObj);

        // 5. 创建订单日志
        OrderLogDO orderLog = new OrderLogDO()
                .setOrderId(orderId)
                .setBeforeStatus(order.getStatus())
                .setAfterStatus(newStatus)
                .setContent(String.format("订单成交: 成交数量 %d, 成交价格 %.3f", fillQuantity, fillPrice))
                .setOperationTime(LocalDateTime.now())
                .setOperatorUserId(order.getUserId()); // 补充操作用户ID
        orderLogService.createOrderLog(orderLog);

        // 6. 发送订单状态变更消息
        orderProducer.sendOrderMessage(new OrderMessage().setOrderId(orderId).setUserId(order.getUserId()).setStatus(newStatus));
        log.info("[processOrderFillInternal][订单({})处理完成，来源MQ：{}]", orderId, fromMq);
    }

    /**
     * 计算手续费
     *
     * @param price 成交价格
     * @param quantity 成交数量
     * @return 手续费
     */
    private BigDecimal calculateCommission(BigDecimal price, Integer quantity) {
        // TODO 根据实际业务规则计算手续费
        BigDecimal amount = price.multiply(new BigDecimal(quantity));
        return amount.multiply(new BigDecimal("0.0003")).setScale(2, RoundingMode.UP); // 暂定千分之三
    }

    /**
     * 计算成交均价
     *
     * @param oldAvgPrice 原成交均价
     * @param oldQuantity 原成交数量
     * @param newPrice 新成交价格
     * @param newQuantity 新成交数量
     * @return 新的成交均价
     */
    private BigDecimal calculateAvgFillPrice(BigDecimal oldAvgPrice, Integer oldQuantity,
                                           BigDecimal newPrice, Integer newQuantity) {
        if (oldAvgPrice == null || oldQuantity == 0) {
            return newPrice;
        }
        BigDecimal totalAmount = oldAvgPrice.multiply(new BigDecimal(oldQuantity))
                .add(newPrice.multiply(new BigDecimal(newQuantity)));
        return totalAmount.divide(new BigDecimal(oldQuantity + newQuantity), 3, RoundingMode.HALF_UP);
    }

}