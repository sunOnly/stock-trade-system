package com.stock.trade.indicator.convert;

import com.stock.trade.framework.common.pojo.PageResult;
import com.stock.trade.indicator.controller.vo.indicator.definition.*;
import com.stock.trade.indicator.dal.dataobject.IndicatorDefinitionDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 指标定义 Convert
 *
 * @author tianxin
 */
@Mapper
public interface IndicatorDefinitionConvert {

    IndicatorDefinitionConvert INSTANCE = Mappers.getMapper(IndicatorDefinitionConvert.class);

    IndicatorDefinitionDO convert(IndicatorDefinitionCreateReqVO bean);

    IndicatorDefinitionDO convert(IndicatorDefinitionUpdateReqVO bean);

    IndicatorDefinitionRespVO convert(IndicatorDefinitionDO bean);

    List<IndicatorDefinitionRespVO> convertList(List<IndicatorDefinitionDO> list);

    PageResult<IndicatorDefinitionRespVO> convertPage(PageResult<IndicatorDefinitionDO> page);

    List<IndicatorDefinitionExcelVO> convertList02(List<IndicatorDefinitionDO> list);

    List<IndicatorDefinitionSimpleRespVO> convertList03(List<IndicatorDefinitionDO> list);

}