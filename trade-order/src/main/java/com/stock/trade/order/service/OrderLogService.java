package com.stock.trade.order.service;

import com.stock.trade.order.dal.dataobject.OrderLogDO;

import java.util.List;

/**
 * 订单日志 Service 接口
 *
 * @author xxx
 */
public interface OrderLogService {

    /**
     * 创建订单日志
     *
     * @param orderLog 订单日志信息
     */
    void createOrderLog(OrderLogDO orderLog);

    /**
     * 根据订单编号获取订单日志列表
     *
     * @param orderId 订单编号
     * @return 订单日志列表
     */
    List<OrderLogDO> getOrderLogListByOrderId(Long orderId);

}