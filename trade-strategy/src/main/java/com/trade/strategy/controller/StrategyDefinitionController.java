package com.trade.strategy.controller;

import com.trade.strategy.entity.StrategyDefinition;
import com.trade.strategy.service.StrategyDefinitionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 交易策略定义API接口
 *
 * @author AI Assistant
 */
@RestController
@RequestMapping("/api/v1/strategies/definitions")
@Tag(name = "StrategyDefinitionController", description = "交易策略定义管理API")
public class StrategyDefinitionController {

    @Autowired
    private StrategyDefinitionService strategyDefinitionService;

    /**
     * 创建新的交易策略定义
     *
     * @param strategyDefinition 策略定义对象
     * @return 创建的策略定义
     */
    @PostMapping
    @Operation(summary = "创建新的交易策略定义")
    public ResponseEntity<StrategyDefinition> createStrategy(@RequestBody StrategyDefinition strategyDefinition) {
        try {
            StrategyDefinition createdStrategy = strategyDefinitionService.createStrategy(strategyDefinition);
            return new ResponseEntity<>(createdStrategy, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // 或者返回更详细的错误信息
        }
    }

    /**
     * 更新已有的交易策略定义
     *
     * @param id                 策略ID
     * @param strategyDefinition 策略定义对象
     * @return 更新后的策略定义
     */
    @PutMapping("/{id}")
    @Operation(summary = "更新已有的交易策略定义")
    public ResponseEntity<StrategyDefinition> updateStrategy(@PathVariable Long id, @RequestBody StrategyDefinition strategyDefinition) {
        try {
            strategyDefinition.setId(id); // 确保ID一致
            StrategyDefinition updatedStrategy = strategyDefinitionService.updateStrategy(strategyDefinition);
            return ResponseEntity.ok(updatedStrategy);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // 或者返回更详细的错误信息
        }
    }

    /**
     * 根据ID删除交易策略定义
     *
     * @param id 策略ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "根据ID删除交易策略定义")
    public ResponseEntity<Void> deleteStrategy(@PathVariable Long id) {
        try {
            strategyDefinitionService.deleteStrategy(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 根据ID查询交易策略定义
     *
     * @param id 策略ID
     * @return 策略定义
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询交易策略定义")
    public ResponseEntity<StrategyDefinition> getStrategyById(@PathVariable Long id) {
        StrategyDefinition strategy = strategyDefinitionService.getStrategyById(id);
        if (strategy != null) {
            return ResponseEntity.ok(strategy);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 查询所有交易策略定义
     *
     * @return 策略定义列表
     */
    @GetMapping
    @Operation(summary = "查询所有交易策略定义")
    public ResponseEntity<List<StrategyDefinition>> getAllStrategies() {
        List<StrategyDefinition> strategies = strategyDefinitionService.getAllStrategies();
        return ResponseEntity.ok(strategies);
    }

    /**
     * 根据策略名称查询交易策略定义
     *
     * @param name 策略名称
     * @return 策略定义
     */
    @GetMapping("/by-name")
    @Operation(summary = "根据策略名称查询交易策略定义")
    public ResponseEntity<StrategyDefinition> getStrategyByName(@RequestParam String name) {
        StrategyDefinition strategy = strategyDefinitionService.getStrategyByName(name);
        if (strategy != null) {
            return ResponseEntity.ok(strategy);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 根据策略类型查询交易策略定义
     *
     * @param type 策略类型
     * @return 策略定义列表
     */
    @GetMapping("/by-type")
    @Operation(summary = "根据策略类型查询交易策略定义")
    public ResponseEntity<List<StrategyDefinition>> getStrategiesByType(@RequestParam String type) {
        List<StrategyDefinition> strategies = strategyDefinitionService.getStrategiesByType(type);
        return ResponseEntity.ok(strategies);
    }

    /**
     * 根据策略状态查询交易策略定义
     *
     * @param status 策略状态
     * @return 策略定义列表
     */
    @GetMapping("/by-status")
    @Operation(summary = "根据策略状态查询交易策略定义")
    public ResponseEntity<List<StrategyDefinition>> getStrategiesByStatus(@RequestParam String status) {
        List<StrategyDefinition> strategies = strategyDefinitionService.getStrategiesByStatus(status);
        return ResponseEntity.ok(strategies);
    }
}