package com.trade.marketdata.controller;

import com.trade.marketdata.entity.DailyMarketData;
import com.trade.marketdata.service.DailyMarketDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 股票日线行情数据 Controller
 *
 * @author Trae
 * @since 2024-07-26
 */
@RestController
@RequestMapping("/market-data/daily")
@RequiredArgsConstructor
@Tag(name = "股票日线行情管理", description = "提供股票日线行情数据的同步和查询功能")
public class DailyMarketDataController {

    private final DailyMarketDataService dailyMarketDataService;

    /**
     * 从Tushare同步指定日期的所有股票日线行情数据
     *
     * @param tradeDate 交易日期，格式 yyyyMMdd
     * @return 同步结果
     */
    @PostMapping("/sync/{tradeDate}")
    @Operation(summary = "同步指定日期的股票日线行情数据", description = "从Tushare同步指定日期的所有股票日线行情数据")
    public String syncDailyMarketData(@PathVariable String tradeDate) {
        dailyMarketDataService.syncDailyMarketData(tradeDate);
        return "Sync daily market data for " + tradeDate + " successfully.";
    }

    /**
     * 从Tushare同步指定日期范围的所有股票日线行情数据
     *
     * @param startDate 开始日期，格式 yyyyMMdd
     * @param endDate   结束日期，格式 yyyyMMdd
     * @return 同步结果
     */
    @PostMapping("/sync/range")
    @Operation(summary = "同步指定日期范围的股票日线行情数据", description = "从Tushare同步指定日期范围内的所有股票日线行情数据")
    public String syncDailyMarketDataByDateRange(@RequestParam String startDate, @RequestParam String endDate) {
        dailyMarketDataService.syncDailyMarketDataByDateRange(startDate, endDate);
        return "Sync daily market data from " + startDate + " to " + endDate + " successfully.";
    }

    /**
     * 查询指定股票在指定日期范围内的日线行情数据
     *
     * @param tsCode    股票代码
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 日线行情数据列表
     */
    @GetMapping("/query")
    @Operation(summary = "查询日线行情数据", description = "查询指定股票在指定日期范围内的日线行情数据")
    public List<DailyMarketData> getDailyMarketData(
            @RequestParam String tsCode,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return dailyMarketDataService.getDailyMarketData(tsCode, startDate, endDate);
    }

    /**
     * 查询指定日期的所有股票日线行情数据
     *
     * @param tradeDate 交易日期
     * @return 日线行情数据列表
     */
    @GetMapping("/query/{tradeDate}")
    @Operation(summary = "查询指定日期的所有股票日线行情数据", description = "查询指定日期的所有股票日线行情数据")
    public List<DailyMarketData> getDailyMarketDataByTradeDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate tradeDate) {
        return dailyMarketDataService.getDailyMarketDataByTradeDate(tradeDate);
    }
}