package com.trade.marketdata.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.trade.marketdata.entity.RealtimeMarketData;
import org.apache.ibatis.annotations.Mapper;

/**
 * 股票实时行情数据 Mapper 接口
 *
 * @author Trae
 * @since 2024-07-26
 */
@Mapper
public interface RealtimeMarketDataMapper extends BaseMapper<RealtimeMarketData> {
}