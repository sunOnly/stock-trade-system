package com.stock.trade.indicator.convert;

import com.stock.trade.framework.common.pojo.PageResult;
import com.stock.trade.indicator.controller.vo.indicator.value.*;
import com.stock.trade.indicator.dal.dataobject.IndicatorValueDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 指标计算结果 Convert
 *
 * @author tianxin
 */
@Mapper
public interface IndicatorValueConvert {

    IndicatorValueConvert INSTANCE = Mappers.getMapper(IndicatorValueConvert.class);

    IndicatorValueDO convert(IndicatorValueCreateReqVO bean);

    IndicatorValueDO convert(IndicatorValueUpdateReqVO bean);

    IndicatorValueRespVO convert(IndicatorValueDO bean);

    List<IndicatorValueRespVO> convertList(List<IndicatorValueDO> list);

    PageResult<IndicatorValueRespVO> convertPage(PageResult<IndicatorValueDO> page);

    List<IndicatorValueExcelVO> convertList02(List<IndicatorValueDO> list);

}