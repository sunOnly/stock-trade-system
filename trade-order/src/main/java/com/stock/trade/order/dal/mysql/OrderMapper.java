package com.stock.trade.order.dal.mysql;

import com.stock.trade.common.core.dal.mapper.BaseMapperX;
import com.stock.trade.common.core.dal.qo.PageQuery;
import com.stock.trade.order.dal.dataobject.OrderDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 订单 Mapper
 *
 * @author xxx
 */
@Mapper
public interface OrderMapper extends BaseMapperX<OrderDO> {

    default List<OrderDO> selectPage(PageQuery pageQuery, Long userId, String stockCode, Integer type, Integer direction, Integer status) {
        return selectList(new LambdaQueryWrapperX<OrderDO>()
                .eqIfPresent(OrderDO::getUserId, userId)
                .likeIfPresent(OrderDO::getStockCode, stockCode)
                .eqIfPresent(OrderDO::getType, type)
                .eqIfPresent(OrderDO::getDirection, direction)
                .eqIfPresent(OrderDO::getStatus, status)
                .orderByDesc(OrderDO::getId),
                pageQuery.getPageNo(), pageQuery.getPageSize());
    }

    default Long selectCount(Long userId, String stockCode, Integer type, Integer direction, Integer status) {
        return selectCount(new LambdaQueryWrapperX<OrderDO>()
                .eqIfPresent(OrderDO::getUserId, userId)
                .likeIfPresent(OrderDO::getStockCode, stockCode)
                .eqIfPresent(OrderDO::getType, type)
                .eqIfPresent(OrderDO::getDirection, direction)
                .eqIfPresent(OrderDO::getStatus, status));
    }

    default List<OrderDO> selectListByUserId(Long userId) {
        return selectList(OrderDO::getUserId, userId);
    }
}