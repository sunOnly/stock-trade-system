package com.trade.strategy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.trade.strategy.entity.StrategyBacktest;
import org.apache.ibatis.annotations.Mapper;

/**
 * 策略回测记录 Mapper 接口
 *
 * @author AI Assistant
 */
@Mapper
public interface StrategyBacktestMapper extends BaseMapper<StrategyBacktest> {

}