package com.stock.marketdata.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stock.common.pojo.CommonResult;
import com.stock.marketdata.dal.dataobject.MarketDataDO;
import com.stock.marketdata.service.MarketDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 行情数据 Controller
 *
 * @author Pure AI
 */
@Tag(name = "行情数据接口")
@RestController
@RequestMapping("/market-data")
public class MarketDataController {

    @Resource
    private MarketDataService marketDataService;

    @PostMapping
    @Operation(summary = "创建行情数据")
    public CommonResult<Long> createMarketData(@RequestBody MarketDataDO marketData) {
        return CommonResult.success(marketDataService.createMarketData(marketData));
    }

    @PutMapping
    @Operation(summary = "更新行情数据")
    public CommonResult<Boolean> updateMarketData(@RequestBody MarketDataDO marketData) {
        marketDataService.updateMarketData(marketData);
        return CommonResult.success(true);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除行情数据")
    @Parameter(name = "id", description = "行情数据编号", required = true, example = "1024")
    public CommonResult<Boolean> deleteMarketData(@PathVariable("id") Long id) {
        marketDataService.deleteMarketData(id);
        return CommonResult.success(true);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取行情数据")
    @Parameter(name = "id", description = "行情数据编号", required = true, example = "1024")
    public CommonResult<MarketDataDO> getMarketData(@PathVariable("id") Long id) {
        return CommonResult.success(marketDataService.getMarketData(id));
    }

    @GetMapping("/list")
    @Operation(summary = "获取行情数据列表")
    @Parameter(name = "stockCode", description = "股票代码", example = "000001")
    public CommonResult<List<MarketDataDO>> getMarketDataList(@RequestParam(required = false) String stockCode) {
        return CommonResult.success(marketDataService.getMarketDataList(stockCode));
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询行情数据")
    public CommonResult<Page<MarketDataDO>> pageMarketData(
            @Parameter(description = "页码", example = "1") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页条数", example = "10") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "股票代码", example = "000001") @RequestParam(required = false) String stockCode) {
        Page<MarketDataDO> page = new Page<>(pageNum, pageSize);
        return CommonResult.success(marketDataService.pageMarketData(page, stockCode));
    }

    // TODO: 添加其他接口

}