package com.stock.marketdata.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stock.marketdata.dal.dataobject.MarketDataDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 行情数据 Mapper
 *
 * @author TraeAI
 */
@Mapper
public interface MarketDataMapper extends BaseMapper<MarketDataDO> {
    // TODO: 定义行情数据相关的数据库操作方法
}