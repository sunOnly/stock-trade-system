package com.trade.marketdata.service;

import com.trade.marketdata.entity.RealtimeMarketData;

import java.util.List;

/**
 * 股票实时行情数据 Service 接口
 *
 * @author Trae
 * @since 2024-07-26
 */
public interface RealtimeMarketDataService {

    /**
     * 获取指定股票代码列表的实时行情数据
     *
     * @param tsCodes 股票代码列表，逗号分隔
     * @return 实时行情数据列表
     */
    List<RealtimeMarketData> getRealtimeMarketData(String tsCodes);

    /**
     * 保存实时行情数据列表
     *
     * @param realtimeMarketDataList 实时行情数据列表
     */
    void saveRealtimeMarketData(List<RealtimeMarketData> realtimeMarketDataList);
}