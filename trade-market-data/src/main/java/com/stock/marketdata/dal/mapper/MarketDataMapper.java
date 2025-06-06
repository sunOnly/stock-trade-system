package com.stock.marketdata.dal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stock.marketdata.dal.dataobject.MarketDataDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 行情数据 Mapper
 *
 * @author Pure AI
 */
@Mapper
public interface MarketDataMapper extends BaseMapper<MarketDataDO> {

    // TODO: 定义自定义的 SQL 查询方法

}