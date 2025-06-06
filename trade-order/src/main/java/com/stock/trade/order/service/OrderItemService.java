package com.stock.trade.order.service;

import com.stock.trade.order.dal.dataobject.OrderItemDO;

import java.util.List;

/**
 * 订单项 Service 接口
 *
 * @author xxx
 */
public interface OrderItemService {

    /**
     * 根据订单编号获取订单项列表
     *
     * @param orderId 订单编号
     * @return 订单项列表
     */
    List<OrderItemDO> getOrderItemListByOrderId(Long orderId);

    /**
     * 根据订单编号列表获取订单项列表
     *
     * @param orderIds 订单编号列表
     * @return 订单项列表
     */
    List<OrderItemDO> getOrderItemListByOrderIds(List<Long> orderIds);

    /**
     * 创建订单项
     *
     * @param orderItem 订单项信息
     * @return 订单项编号
     */
    Long createOrderItem(OrderItemDO orderItem);

}