package com.stock.trade.order.service.impl;

import com.stock.trade.order.dal.dataobject.OrderLogDO;
import com.stock.trade.order.dal.mysql.OrderLogMapper;
import com.stock.trade.order.service.OrderLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订单日志 Service 实现类
 *
 * @author xxx
 */
@Service
@Slf4j
public class OrderLogServiceImpl implements OrderLogService {

    @Resource
    private OrderLogMapper orderLogMapper;

    @Override
    public void createOrderLog(OrderLogDO orderLog) {
        orderLogMapper.insert(orderLog);
    }

    @Override
    public List<OrderLogDO> getOrderLogListByOrderId(Long orderId) {
        return orderLogMapper.selectListByOrderId(orderId);
    }

}