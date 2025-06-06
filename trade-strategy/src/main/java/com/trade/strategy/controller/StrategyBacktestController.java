package com.trade.strategy.controller;

import com.trade.strategy.entity.StrategyBacktest;
import com.trade.strategy.service.StrategyBacktestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 策略回测API接口
 *
 * @author AI Assistant
 */
@RestController
@RequestMapping("/api/v1/strategies/backtests")
@Tag(name = "StrategyBacktestController", description = "策略回测管理API")
public class StrategyBacktestController {

    @Autowired
    private StrategyBacktestService strategyBacktestService;

    /**
     * 创建新的策略回测任务
     *
     * @param strategyBacktest 回测任务基本信息
     * @return 创建的回测任务
     */
    @PostMapping
    @Operation(summary = "创建新的策略回测任务")
    public ResponseEntity<StrategyBacktest> createBacktest(@RequestBody StrategyBacktest strategyBacktest) {
        try {
            StrategyBacktest createdBacktest = strategyBacktestService.createBacktest(strategyBacktest);
            return new ResponseEntity<>(createdBacktest, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Consider returning error message
        }
    }

    /**
     * 启动一个指定ID的回测任务
     *
     * @param backtestId 回测任务ID
     * @return 启动后的回测任务状态
     */
    @PostMapping("/{backtestId}/start")
    @Operation(summary = "启动一个指定ID的回测任务")
    public ResponseEntity<StrategyBacktest> startBacktest(@PathVariable Long backtestId) {
        try {
            StrategyBacktest startedBacktest = strategyBacktestService.startBacktest(backtestId);
            return ResponseEntity.ok(startedBacktest);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(null); // Consider returning error message
        }
    }

    /**
     * (模拟) 更新回测任务的状态和结果 - 实际应由回测引擎回调
     *
     * @param backtestId 回测任务ID
     * @param payload    包含 status 和 resultDetails 的请求体
     * @return 更新后的回测任务
     */
    @PutMapping("/{backtestId}/update-result") // This endpoint is more for internal/engine callback simulation
    @Operation(summary = "(模拟)更新回测任务的状态和结果", description = "实际场景中此接口可能由回测引擎内部调用或通过消息队列触发")
    public ResponseEntity<StrategyBacktest> updateBacktestResult(
            @PathVariable Long backtestId,
            @RequestBody Map<String, String> payload) {
        try {
            String status = payload.get("status");
            String resultDetails = payload.get("resultDetails");
            if (status == null || resultDetails == null) {
                return ResponseEntity.badRequest().body(null); // Missing parameters
            }
            StrategyBacktest updatedBacktest = strategyBacktestService.updateBacktestStatusAndResult(backtestId, status, resultDetails);
            return ResponseEntity.ok(updatedBacktest);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    /**
     * 根据ID查询策略回测记录
     *
     * @param backtestId 回测任务ID
     * @return 回测任务详情
     */
    @GetMapping("/{backtestId}")
    @Operation(summary = "根据ID查询策略回测记录")
    public ResponseEntity<StrategyBacktest> getBacktestById(@PathVariable Long backtestId) {
        StrategyBacktest backtest = strategyBacktestService.getBacktestById(backtestId);
        if (backtest != null) {
            return ResponseEntity.ok(backtest);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 查询指定策略定义的所有回测记录
     *
     * @param strategyDefinitionId 策略定义ID
     * @return 回测记录列表
     */
    @GetMapping("/by-definition/{strategyDefinitionId}")
    @Operation(summary = "查询指定策略定义的所有回测记录")
    public ResponseEntity<List<StrategyBacktest>> getBacktestsByStrategyDefinitionId(@PathVariable Long strategyDefinitionId) {
        List<StrategyBacktest> backtests = strategyBacktestService.getBacktestsByStrategyDefinitionId(strategyDefinitionId);
        return ResponseEntity.ok(backtests);
    }

    /**
     * 查询所有策略回测记录
     *
     * @return 所有回测记录列表
     */
    @GetMapping
    @Operation(summary = "查询所有策略回测记录")
    public ResponseEntity<List<StrategyBacktest>> getAllBacktests() {
        List<StrategyBacktest> backtests = strategyBacktestService.getAllBacktests();
        return ResponseEntity.ok(backtests);
    }

    /**
     * 根据ID删除策略回测记录
     *
     * @param backtestId 回测任务ID
     * @return 操作结果
     */
    @DeleteMapping("/{backtestId}")
    @Operation(summary = "根据ID删除策略回测记录")
    public ResponseEntity<Void> deleteBacktest(@PathVariable Long backtestId) {
        try {
            strategyBacktestService.deleteBacktest(backtestId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Or another appropriate status for non-deletable state
        }
    }
}