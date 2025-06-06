package com.trade.strategy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.trade.strategy.entity.StrategyDefinition;

import java.util.List;

/**
 * 交易策略定义服务接口
 *
 * @author AI Assistant
 */
public interface StrategyDefinitionService extends IService<StrategyDefinition> {

    /**
     * 创建新的交易策略定义
     *
     * @param strategyDefinition 策略定义对象
     * @return 创建成功后的策略定义对象，包含生成的ID
     */
    StrategyDefinition createStrategy(StrategyDefinition strategyDefinition);

    /**
     * 更新已有的交易策略定义
     *
     * @param strategyDefinition 策略定义对象
     * @return 更新成功后的策略定义对象
     */
    StrategyDefinition updateStrategy(StrategyDefinition strategyDefinition);

    /**
     * 根据ID删除交易策略定义
     *
     * @param id 策略ID
     */
    void deleteStrategy(Long id);

    /**
     * 根据ID查询交易策略定义
     *
     * @param id 策略ID
     * @return 策略定义对象，如果不存在则返回null
     */
    StrategyDefinition getStrategyById(Long id);

    /**
     * 查询所有交易策略定义
     *
     * @return 策略定义列表
     */
    List<StrategyDefinition> getAllStrategies();

    /**
     * 根据策略名称查询交易策略定义
     *
     * @param name 策略名称
     * @return 策略定义对象，如果不存在则返回null
     */
    StrategyDefinition getStrategyByName(String name);

    /**
     * 根据策略类型查询交易策略定义
     *
     * @param type 策略类型
     * @return 符合类型的策略定义列表
     */
    List<StrategyDefinition> getStrategiesByType(String type);

    /**
     * 根据策略状态查询交易策略定义
     *
     * @param status 策略状态
     * @return 符合状态的策略定义列表
     */
    List<StrategyDefinition> getStrategiesByStatus(String status);

}