package com.trade.marketdata.controller;

import com.trade.common.api.CommonResult;
import com.trade.marketdata.entity.StockBasic;
import com.trade.marketdata.service.StockBasicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author mac
 * @date 2024/7/16
 * @description 股票基本信息控制器
 */
@RestController
@RequestMapping("/stockBasic")
@Tag(name = "StockBasicController", description = "股票基本信息管理")
public class StockBasicController {

    private final StockBasicService stockBasicService;

    public StockBasicController(StockBasicService stockBasicService) {
        this.stockBasicService = stockBasicService;
    }

    /**
     * 同步股票基本信息
     * @return 同步结果
     */
    @Operation(summary = "同步股票基本信息")
    @PostMapping("/sync")
    public CommonResult<Integer> syncStockBasic() {
        int count = stockBasicService.syncStockBasicFromTushare();
        return CommonResult.success(count, "成功同步 " + count + " 条股票基本信息");
    }

    /**
     * 获取所有股票基本信息
     * @return 股票基本信息列表
     */
    @Operation(summary = "获取所有股票基本信息")
    @GetMapping("/listAll")
    public CommonResult<List<StockBasic>> listAllStockBasic() {
        List<StockBasic> stockBasics = stockBasicService.listAllStockBasic();
        return CommonResult.success(stockBasics);
    }
}