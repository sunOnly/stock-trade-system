package com.trade.strategy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.trade.strategy.entity.StrategyBacktest;

import java.util.List;

/**
 * 策略回测服务接口
 *
 * @author AI Assistant
 */
public interface StrategyBacktestService extends IService<StrategyBacktest> {

    /**
     * 创建并初始化一个新的策略回测任务
     *
     * @param strategyBacktest 回测任务基本信息 (strategyDefinitionId, backtestName, startDate, endDate, initialCapital, commissionRate 必须)
     * @return 创建成功后的回测任务对象，包含生成的ID和初始状态
     */
    StrategyBacktest createBacktest(StrategyBacktest strategyBacktest);

    /**
     * 启动一个指定ID的回测任务
     * (实际回测逻辑可能异步执行)
     *
     * @param backtestId 回测任务ID
     * @return 更新状态后的回测任务对象 (例如: 状态变为RUNNING)
     */
    StrategyBacktest startBacktest(Long backtestId);

    /**
     * 更新回测任务的状态和结果
     * (通常在异步回测任务完成后调用)
     *
     * @param backtestId    回测任务ID
     * @param status        新的状态 (例如: "COMPLETED", "FAILED")
     * @param resultDetails 回测结果详情 (JSON格式)
     * @return 更新后的回测任务对象
     */
    StrategyBacktest updateBacktestStatusAndResult(Long backtestId, String status, String resultDetails);

    /**
     * 根据ID查询策略回测记录
     *
     * @param backtestId 回测任务ID
     * @return 回测任务对象，如果不存在则返回null
     */
    StrategyBacktest getBacktestById(Long backtestId);

    /**
     * 查询指定策略定义的所有回测记录
     *
     * @param strategyDefinitionId 策略定义ID
     * @return 该策略的回测记录列表
     */
    List<StrategyBacktest> getBacktestsByStrategyDefinitionId(Long strategyDefinitionId);

    /**
     * 查询所有策略回测记录
     *
     * @return 所有回测记录列表
     */
    List<StrategyBacktest> getAllBacktests();

    /**
     * 根据ID删除策略回测记录
     * (通常只允许删除未开始或已失败的回测)
     *
     * @param backtestId 回测任务ID
     */
    void deleteBacktest(Long backtestId);

}