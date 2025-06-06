package com.trade.marketdata.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.trade.marketdata.entity.DailyMarketData;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author mac
 * @date 2024/7/16
 * @description 股票日线行情数据 Mapper 接口
 */
@Mapper
public interface DailyMarketDataMapper extends BaseMapper<DailyMarketData> {
}