package com.stock.trade.order.convert;

import com.stock.trade.order.controller.response.OrderLogRespVO;
import com.stock.trade.order.dal.dataobject.OrderLogDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OrderLogConvert {

    OrderLogConvert INSTANCE = Mappers.getMapper(OrderLogConvert.class);

    OrderLogRespVO convert(OrderLogDO bean);

    List<OrderLogRespVO> convertList(List<OrderLogDO> list);

}
