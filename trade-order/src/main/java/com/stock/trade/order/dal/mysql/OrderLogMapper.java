package com.stock.trade.order.dal.mysql;

import com.stock.trade.common.core.dal.mapper.BaseMapperX;
import com.stock.trade.order.dal.dataobject.OrderLogDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 订单日志 Mapper
 *
 * @author xxx
 */
@Mapper
public interface OrderLogMapper extends BaseMapperX<OrderLogDO> {

    default List<OrderLogDO> selectListByOrderId(Long orderId) {
        return selectList(new LambdaQueryWrapperX<OrderLogDO>()
                .eq(OrderLogDO::getOrderId, orderId)
                .orderByDesc(OrderLogDO::getId)); // 按时间倒序，最新的日志在前面
    }
}