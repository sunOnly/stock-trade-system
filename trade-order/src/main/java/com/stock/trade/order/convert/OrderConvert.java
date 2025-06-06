package com.stock.trade.order.convert;

import com.stock.trade.common.core.domain.PageResult;
import com.stock.trade.order.controller.request.OrderCreateReqVO;
import com.stock.trade.order.controller.response.OrderRespVO;
import com.stock.trade.order.dal.dataobject.OrderDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderConvert {

    OrderConvert INSTANCE = Mappers.getMapper(OrderConvert.class);

    OrderDO convert(OrderCreateReqVO bean);

    OrderRespVO convert(OrderDO bean);

    PageResult<OrderRespVO> convertPage(PageResult<OrderDO> page);

}