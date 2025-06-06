package com.yudao.module.strategy.service.execution;

import com.yudao.module.strategy.controller.admin.execution.vo.StrategyExecutionCreateReqVO;
import com.yudao.module.strategy.controller.admin.execution.vo.StrategyExecutionPageReqVO;
import com.yudao.module.strategy.controller.admin.execution.vo.StrategyExecutionUpdateReqVO;
import com.yudao.module.strategy.dal.dataobject.execution.StrategyExecution;
import com.yudao.framework.common.pojo.PageResult;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 策略执行 Service 接口
 */
public interface StrategyExecutionService {

    /**
     * 创建策略执行记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createStrategyExecution(@Valid StrategyExecutionCreateReqVO createReqVO);

    /**
     * 更新策略执行记录
     *
     * @param updateReqVO 更新信息
     */
    void updateStrategyExecution(@Valid StrategyExecutionUpdateReqVO updateReqVO);

    /**
     * 启动策略执行
     *
     * @param id 策略执行编号
     */
    void startStrategyExecution(Long id);

    /**
     * 暂停策略执行
     *
     * @param id 策略执行编号
     */
    void pauseStrategyExecution(Long id);

    /**
     * 停止策略执行
     *
     * @param id 策略执行编号
     */
    void stopStrategyExecution(Long id);

    /**
     * 删除策略执行记录
     *
     * @param id 编号
     */
    void deleteStrategyExecution(Long id);

    /**
     * 获得策略执行记录
     *
     * @param id 编号
     * @return 策略执行记录
     */
    StrategyExecution getStrategyExecution(Long id);

    /**
     * 获得策略执行记录列表
     *
     * @param ids 编号列表
     * @return 策略执行记录列表
     */
    List<StrategyExecution> getStrategyExecutionList(Collection<Long> ids);

    /**
     * 获得策略执行记录分页
     *
     * @param pageReqVO 分页查询
     * @return 策略执行记录分页
     */
    PageResult<StrategyExecution> getStrategyExecutionPage(StrategyExecutionPageReqVO pageReqVO);

    /**
     * 根据策略定义ID查询执行记录列表
     *
     * @param strategyDefinitionId 策略定义ID
     * @return 策略执行记录列表
     */
    List<StrategyExecution> getStrategyExecutionsByDefinitionId(Long strategyDefinitionId);

}