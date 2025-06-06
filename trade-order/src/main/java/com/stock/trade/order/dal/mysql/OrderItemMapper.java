package com.stock.trade.order.dal.mysql;

import com.stock.trade.common.core.dal.mapper.BaseMapperX;
import com.stock.trade.order.dal.dataobject.OrderItemDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 订单项 Mapper
 *
 * @author xxx
 */
@Mapper
public interface OrderItemMapper extends BaseMapperX<OrderItemDO> {

    default List<OrderItemDO> selectListByOrderId(Long orderId) {
        return selectList(OrderItemDO::getOrderId, orderId);
    }

    default List<OrderItemDO> selectListByOrderIds(List<Long> orderIds) {
        return selectList(new LambdaQueryWrapperX<OrderItemDO>()
                .in(OrderItemDO::getOrderId, orderIds));
    }
}