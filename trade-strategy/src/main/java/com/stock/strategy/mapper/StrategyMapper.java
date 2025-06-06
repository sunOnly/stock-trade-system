package com.stock.strategy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stock.strategy.dal.dataobject.StrategyDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 策略 Mapper
 *
 * @author TraeAI
 */
@Mapper
public interface StrategyMapper extends BaseMapper<StrategyDO> {
    // TODO: 定义策略相关的数据库操作方法
}