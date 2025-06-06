package com.yudao.module.strategy.dal.mysql.execution;

import com.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.yudao.module.strategy.dal.dataobject.execution.StrategyExecution;
import org.apache.ibatis.annotations.Mapper;

/**
 * 策略执行记录 Mapper
 */
@Mapper
public interface StrategyExecutionMapper extends BaseMapperX<StrategyExecution> {

    // 可以在此定义特定的查询方法，例如根据策略定义ID查询执行记录等

}