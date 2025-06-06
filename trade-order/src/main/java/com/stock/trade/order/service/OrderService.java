package com.stock.trade.order.service;

import com.stock.trade.common.core.domain.PageResult;
import com.stock.trade.order.controller.request.OrderCreateReqVO;
import com.stock.trade.order.controller.request.OrderPageReqVO;
import com.stock.trade.order.controller.request.OrderUpdateReqVO;
import com.stock.trade.order.dal.dataobject.OrderDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 订单 Service 接口
 *
 * @author xxx
 */
public interface OrderService {

    /**
     * 创建订单
     *
     * @param createReqVO 创建信息
     * @return 订单编号
     */
    Long createOrder(@Valid OrderCreateReqVO createReqVO);

    /**
     * 更新订单
     *
     * @param updateReqVO 更新信息
     */
    void updateOrder(@Valid OrderUpdateReqVO updateReqVO);

    /**
     * 更新订单状态
     *
     * @param id 订单编号
     * @param status 订单状态
     * @param remark 备注 (可选)
     */
    void updateOrderStatus(Long id, Integer status, String remark);

    /**
     * 撤销订单
     *
     * @param id 订单编号
     * @param userId 用户编号 (用于权限校验)
     */
    void cancelOrder(Long id, Long userId);

    /**
     * 删除订单 (逻辑删除)
     *
     * @param id 订单编号
     */
    void deleteOrder(Long id);

    /**
     * 获取订单信息
     *
     * @param id 订单编号
     * @return 订单信息
     */
    OrderDO getOrder(Long id);

    /**
     * 获取订单列表
     *
     * @param ids 订单编号集合
     * @return 订单列表
     */
    List<OrderDO> getOrderList(Collection<Long> ids);

    /**
     * 获取订单分页
     *
     * @param pageReqVO 分页查询参数
     * @return 订单分页结果
     */
    PageResult<OrderDO> getOrderPage(OrderPageReqVO pageReqVO);

    /**
     * 根据用户编号获取其所有未完成订单列表
     *
     * @param userId 用户编号
     * @return 未完成订单列表
     */
    List<OrderDO> getUnfinishedOrdersByUserId(Long userId);

    /**
     * 处理订单成交回报
     *
     * @param orderId 订单编号
     * @param fillPrice 成交价格
     * @param fillQuantity 成交数量
     * @param tradeNo 成交编号
     */
    void processOrderFill(Long orderId, String stockCode, Integer direction, java.math.BigDecimal fillPrice, Integer fillQuantity, String tradeNo);

}