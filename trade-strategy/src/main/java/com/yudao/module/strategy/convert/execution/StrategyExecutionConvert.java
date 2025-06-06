package com.yudao.module.strategy.convert.execution;

import com.yudao.framework.common.pojo.PageResult;
import com.yudao.module.strategy.controller.admin.execution.vo.StrategyExecutionCreateReqVO;
import com.yudao.module.strategy.controller.admin.execution.vo.StrategyExecutionRespVO;
import com.yudao.module.strategy.controller.admin.execution.vo.StrategyExecutionUpdateReqVO;
import com.yudao.module.strategy.dal.dataobject.execution.StrategyExecution;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 策略执行 Convert
 */
@Mapper
public interface StrategyExecutionConvert {

    StrategyExecutionConvert INSTANCE = Mappers.getMapper(StrategyExecutionConvert.class);

    /**
     * 创建请求 VO 转换为 DO
     */
    StrategyExecution convert(StrategyExecutionCreateReqVO bean);

    /**
     * 更新请求 VO 转换为 DO
     */
    StrategyExecution convert(StrategyExecutionUpdateReqVO bean);

    /**
     * DO 转换为响应 VO
     */
    StrategyExecutionRespVO convert(StrategyExecution bean);

    /**
     * DO 列表转换为响应 VO 列表
     */
    List<StrategyExecutionRespVO> convertList(List<StrategyExecution> list);

    /**
     * 分页结果转换
     */
    PageResult<StrategyExecutionRespVO> convertPage(PageResult<StrategyExecution> page);

}