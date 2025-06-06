package com.trade.marketdata.controller;

import com.trade.marketdata.entity.RealtimeMarketData;
import com.trade.marketdata.service.RealtimeMarketDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 股票实时行情数据 Controller
 *
 * @author Trae
 * @since 2024-07-26
 */
@RestController
@RequestMapping("/market-data/realtime")
@RequiredArgsConstructor
@Tag(name = "股票实时行情管理", description = "提供股票实时行情数据的查询功能")
public class RealtimeMarketDataController {

    private final RealtimeMarketDataService realtimeMarketDataService;

    /**
     * 获取指定股票代码列表的实时行情数据
     *
     * @param tsCodes 股票代码列表，逗号分隔，例如 "600000.SH,000001.SZ"
     * @return 实时行情数据列表
     */
    @GetMapping("/query")
    @Operation(summary = "查询实时行情数据", description = "获取指定股票代码列表的实时行情数据")
    public List<RealtimeMarketData> getRealtimeMarketData(@RequestParam String tsCodes) {
        List<RealtimeMarketData> realtimeData = realtimeMarketDataService.getRealtimeMarketData(tsCodes);
        // 可以选择是否在这里保存获取到的数据
        // realtimeMarketDataService.saveRealtimeMarketData(realtimeData);
        return realtimeData;
    }

    /**
     * 获取并保存指定股票代码列表的实时行情数据
     *
     * @param tsCodes 股票代码列表，逗号分隔
     * @return 操作结果
     */
    @PostMapping("/sync-and-save")
    @Operation(summary = "同步并保存实时行情数据", description = "获取并保存指定股票代码列表的实时行情数据")
    public String syncAndSaveRealtimeMarketData(@RequestParam String tsCodes) {
        List<RealtimeMarketData> realtimeData = realtimeMarketDataService.getRealtimeMarketData(tsCodes);
        if (realtimeData != null && !realtimeData.isEmpty()) {
            realtimeMarketDataService.saveRealtimeMarketData(realtimeData);
            return "Successfully fetched and saved realtime market data for: " + tsCodes;
        }
        return "No realtime market data found for: " + tsCodes;
    }
}