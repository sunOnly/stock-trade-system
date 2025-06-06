package com.stock.trade.order.convert;

import com.stock.trade.order.controller.response.OrderItemRespVO;
import com.stock.trade.order.dal.dataobject.OrderItemDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OrderItemConvert {

    OrderItemConvert INSTANCE = Mappers.getMapper(OrderItemConvert.class);

    OrderItemRespVO convert(OrderItemDO bean);

    List<OrderItemRespVO> convertList(List<OrderItemDO> list);

}