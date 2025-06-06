package com.stock.trade.order.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.stock.trade.order.dal.dataobject.OrderItemDO;
import com.stock.trade.order.dal.mysql.OrderItemMapper;
import com.stock.trade.order.service.OrderItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * 订单项 Service 实现类
 *
 * @author xxx
 */
@Service
@Slf4j
public class OrderItemServiceImpl implements OrderItemService {

    @Resource
    private OrderItemMapper orderItemMapper;

    @Override
    public List<OrderItemDO> getOrderItemListByOrderId(Long orderId) {
        return orderItemMapper.selectListByOrderId(orderId);
    }

    @Override
    public List<OrderItemDO> getOrderItemListByOrderIds(List<Long> orderIds) {
        if (CollUtil.isEmpty(orderIds)) {
            return Collections.emptyList();
        }
        return orderItemMapper.selectListByOrderIds(orderIds);
    }

    @Override
    public Long createOrderItem(OrderItemDO orderItem) {
        orderItemMapper.insert(orderItem);
        return orderItem.getId();
    }

}