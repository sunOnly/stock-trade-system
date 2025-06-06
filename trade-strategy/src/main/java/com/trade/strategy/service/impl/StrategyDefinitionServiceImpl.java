package com.trade.strategy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trade.strategy.entity.StrategyDefinition;
import com.trade.strategy.mapper.StrategyDefinitionMapper;
import com.trade.strategy.service.StrategyDefinitionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 交易策略定义服务实现类
 *
 * @author AI Assistant
 */
@Service
public class StrategyDefinitionServiceImpl extends ServiceImpl<StrategyDefinitionMapper, StrategyDefinition> implements StrategyDefinitionService {

    /**
     * 创建新的交易策略定义
     *
     * @param strategyDefinition 策略定义对象
     * @return 创建成功后的策略定义对象，包含生成的ID
     */
    @Override
    @Transactional
    public StrategyDefinition createStrategy(StrategyDefinition strategyDefinition) {
        // 可以在这里添加校验逻辑，例如名称是否唯一等
        if (!StringUtils.hasText(strategyDefinition.getName())) {
            throw new IllegalArgumentException("策略名称不能为空");
        }
        StrategyDefinition existing = getStrategyByName(strategyDefinition.getName());
        if (existing != null) {
            throw new IllegalArgumentException("策略名称已存在: " + strategyDefinition.getName());
        }

        strategyDefinition.setCreateTime(LocalDateTime.now());
        strategyDefinition.setUpdateTime(LocalDateTime.now());
        // 默认状态可以设置为 DRAFT
        if (!StringUtils.hasText(strategyDefinition.getStatus())){
            strategyDefinition.setStatus("DRAFT");
        }
        this.save(strategyDefinition);
        return strategyDefinition;
    }

    /**
     * 更新已有的交易策略定义
     *
     * @param strategyDefinition 策略定义对象
     * @return 更新成功后的策略定义对象
     */
    @Override
    @Transactional
    public StrategyDefinition updateStrategy(StrategyDefinition strategyDefinition) {
        if (strategyDefinition.getId() == null) {
            throw new IllegalArgumentException("更新策略时，策略ID不能为空");
        }
        StrategyDefinition existing = getById(strategyDefinition.getId());
        if (existing == null) {
            throw new IllegalArgumentException("未找到要更新的策略，ID: " + strategyDefinition.getId());
        }

        // 如果名称被修改，需要检查新名称是否已存在（排除自身）
        if (StringUtils.hasText(strategyDefinition.getName()) && !strategyDefinition.getName().equals(existing.getName())){
            StrategyDefinition byNewName = getStrategyByName(strategyDefinition.getName());
            if(byNewName != null && !byNewName.getId().equals(strategyDefinition.getId())){
                 throw new IllegalArgumentException("策略名称已存在: " + strategyDefinition.getName());
            }
        }

        strategyDefinition.setUpdateTime(LocalDateTime.now());
        this.updateById(strategyDefinition);
        return getById(strategyDefinition.getId()); // 返回更新后的完整对象
    }

    /**
     * 根据ID删除交易策略定义
     *
     * @param id 策略ID
     */
    @Override
    @Transactional
    public void deleteStrategy(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("删除策略时，策略ID不能为空");
        }
        if (getById(id) == null) {
            throw new IllegalArgumentException("未找到要删除的策略，ID: " + id);
        }
        this.removeById(id);
    }

    /**
     * 根据ID查询交易策略定义
     *
     * @param id 策略ID
     * @return 策略定义对象，如果不存在则返回null
     */
    @Override
    public StrategyDefinition getStrategyById(Long id) {
        return this.getById(id);
    }

    /**
     * 查询所有交易策略定义
     *
     * @return 策略定义列表
     */
    @Override
    public List<StrategyDefinition> getAllStrategies() {
        return this.list();
    }

    /**
     * 根据策略名称查询交易策略定义
     *
     * @param name 策略名称
     * @return 策略定义对象，如果不存在则返回null
     */
    @Override
    public StrategyDefinition getStrategyByName(String name) {
        if (!StringUtils.hasText(name)) {
            return null;
        }
        return this.getOne(new QueryWrapper<StrategyDefinition>().eq("name", name));
    }

    /**
     * 根据策略类型查询交易策略定义
     *
     * @param type 策略类型
     * @return 符合类型的策略定义列表
     */
    @Override
    public List<StrategyDefinition> getStrategiesByType(String type) {
        if (!StringUtils.hasText(type)) {
            return this.list(); // 如果类型为空，返回所有
        }
        return this.list(new QueryWrapper<StrategyDefinition>().eq("type", type));
    }

    /**
     * 根据策略状态查询交易策略定义
     *
     * @param status 策略状态
     * @return 符合状态的策略定义列表
     */
    @Override
    public List<StrategyDefinition> getStrategiesByStatus(String status) {
        if (!StringUtils.hasText(status)) {
            return this.list(); // 如果状态为空，返回所有
        }
        return this.list(new QueryWrapper<StrategyDefinition>().eq("status", status));
    }
}