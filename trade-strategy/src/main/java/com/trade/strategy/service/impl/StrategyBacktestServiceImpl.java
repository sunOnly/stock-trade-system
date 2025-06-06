package com.trade.strategy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trade.strategy.entity.StrategyBacktest;
import com.trade.strategy.entity.StrategyDefinition;
import com.trade.strategy.mapper.StrategyBacktestMapper;
import com.trade.strategy.service.StrategyBacktestService;
import com.trade.strategy.service.StrategyDefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 策略回测服务实现类
 *
 * @author AI Assistant
 */
@Service
public class StrategyBacktestServiceImpl extends ServiceImpl<StrategyBacktestMapper, StrategyBacktest> implements StrategyBacktestService {

    @Autowired
    private StrategyDefinitionService strategyDefinitionService; // 用于校验策略定义是否存在

    // 假设有一个异步执行回测的组件，这里仅作演示
    // @Autowired
    // private BacktestExecutionEngine backtestExecutionEngine;

    /**
     * 创建并初始化一个新的策略回测任务
     *
     * @param strategyBacktest 回测任务基本信息
     * @return 创建成功后的回测任务对象
     */
    @Override
    @Transactional
    public StrategyBacktest createBacktest(StrategyBacktest strategyBacktest) {
        // 参数校验
        if (strategyBacktest.getStrategyDefinitionId() == null) {
            throw new IllegalArgumentException("策略定义ID不能为空");
        }
        StrategyDefinition definition = strategyDefinitionService.getStrategyById(strategyBacktest.getStrategyDefinitionId());
        if (definition == null) {
            throw new IllegalArgumentException("指定的策略定义不存在: ID " + strategyBacktest.getStrategyDefinitionId());
        }
        if (!StringUtils.hasText(strategyBacktest.getBacktestName())) {
            throw new IllegalArgumentException("回测名称不能为空");
        }
        if (strategyBacktest.getStartDate() == null || strategyBacktest.getEndDate() == null) {
            throw new IllegalArgumentException("回测开始或结束日期不能为空");
        }
        if (strategyBacktest.getStartDate().isAfter(strategyBacktest.getEndDate())) {
            throw new IllegalArgumentException("回测开始日期不能晚于结束日期");
        }
        if (strategyBacktest.getInitialCapital() == null || strategyBacktest.getInitialCapital().doubleValue() <= 0) {
            throw new IllegalArgumentException("初始资金必须大于0");
        }
        // 手续费率可以为0
        if (strategyBacktest.getCommissionRate() == null || strategyBacktest.getCommissionRate().doubleValue() < 0) {
            strategyBacktest.setCommissionRate(java.math.BigDecimal.ZERO);
        }

        strategyBacktest.setStatus("PENDING"); // 初始状态为待处理
        strategyBacktest.setCreateTime(LocalDateTime.now());
        strategyBacktest.setUpdateTime(LocalDateTime.now());
        this.save(strategyBacktest);
        return strategyBacktest;
    }

    /**
     * 启动一个指定ID的回测任务
     *
     * @param backtestId 回测任务ID
     * @return 更新状态后的回测任务对象
     */
    @Override
    @Transactional
    public StrategyBacktest startBacktest(Long backtestId) {
        StrategyBacktest backtest = getBacktestById(backtestId);
        if (backtest == null) {
            throw new IllegalArgumentException("未找到要启动的回测任务，ID: " + backtestId);
        }
        if (!"PENDING".equals(backtest.getStatus())) {
            throw new IllegalStateException("回测任务当前状态为 " + backtest.getStatus() + "，无法启动。");
        }

        backtest.setStatus("RUNNING");
        backtest.setUpdateTime(LocalDateTime.now());
        this.updateById(backtest);

        // 实际场景中，这里会触发异步回测引擎执行回测
        // backtestExecutionEngine.execute(backtest);
        // log.info("策略回测任务 [{}] 已启动", backtestId);

        return backtest;
    }

    /**
     * 更新回测任务的状态和结果
     *
     * @param backtestId    回测任务ID
     * @param status        新的状态
     * @param resultDetails 回测结果详情
     * @return 更新后的回测任务对象
     */
    @Override
    @Transactional
    public StrategyBacktest updateBacktestStatusAndResult(Long backtestId, String status, String resultDetails) {
        StrategyBacktest backtest = getBacktestById(backtestId);
        if (backtest == null) {
            throw new IllegalArgumentException("未找到要更新的回测任务，ID: " + backtestId);
        }

        backtest.setStatus(status);
        backtest.setResultDetails(resultDetails);
        backtest.setUpdateTime(LocalDateTime.now());
        this.updateById(backtest);
        return backtest;
    }

    /**
     * 根据ID查询策略回测记录
     *
     * @param backtestId 回测任务ID
     * @return 回测任务对象
     */
    @Override
    public StrategyBacktest getBacktestById(Long backtestId) {
        return this.getById(backtestId);
    }

    /**
     * 查询指定策略定义的所有回测记录
     *
     * @param strategyDefinitionId 策略定义ID
     * @return 回测记录列表
     */
    @Override
    public List<StrategyBacktest> getBacktestsByStrategyDefinitionId(Long strategyDefinitionId) {
        if (strategyDefinitionId == null) {
            return List.of();
        }
        return this.list(new QueryWrapper<StrategyBacktest>().eq("strategy_definition_id", strategyDefinitionId)
                .orderByDesc("create_time")); // 按创建时间降序
    }

    /**
     * 查询所有策略回测记录
     *
     * @return 所有回测记录列表
     */
    @Override
    public List<StrategyBacktest> getAllBacktests() {
        return this.list(new QueryWrapper<StrategyBacktest>().orderByDesc("create_time"));
    }

    /**
     * 根据ID删除策略回测记录
     *
     * @param backtestId 回测任务ID
     */
    @Override
    @Transactional
    public void deleteBacktest(Long backtestId) {
        StrategyBacktest backtest = getBacktestById(backtestId);
        if (backtest == null) {
            throw new IllegalArgumentException("未找到要删除的回测任务，ID: " + backtestId);
        }
        // 通常只允许删除 PENDING 或 FAILED 的回测，或者根据业务需求调整
        if ("RUNNING".equals(backtest.getStatus()) || "COMPLETED".equals(backtest.getStatus())) {
            // throw new IllegalStateException("无法删除正在运行或已完成的回测任务。");
        }
        this.removeById(backtestId);
    }
}