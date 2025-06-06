package com.trade.marketdata.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.trade.marketdata.entity.DailyMarketData;

import java.time.LocalDate;
import java.util.List;

/**
 * @author mac
 * @date 2024/7/16
 * @description 股票日线行情数据服务接口
 */
public interface DailyMarketDataService extends IService<DailyMarketData> {

    /**
     * 从 Tushare 同步指定股票的日线行情数据
     * @param tsCode 股票代码
     * @param startDate 开始日期 (yyyyMMdd)
     * @param endDate 结束日期 (yyyyMMdd)
     * @return 同步的日线数据数量
     */
    int syncDailyMarketDataFromTushare(String tsCode, String startDate, String endDate);

    /**
     * 查询指定股票在指定日期范围内的日线行情数据
     * @param tsCode 股票代码
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 日线行情数据列表
     */
    List<DailyMarketData> listDailyMarketData(String tsCode, LocalDate startDate, LocalDate endDate);
}