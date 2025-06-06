package com.yudao.module.strategy.service.execution;

import com.yudao.framework.common.pojo.PageResult;
import com.yudao.module.strategy.controller.admin.execution.vo.StrategyExecutionCreateReqVO;
import com.yudao.module.strategy.controller.admin.execution.vo.StrategyExecutionPageReqVO;
import com.yudao.module.strategy.controller.admin.execution.vo.StrategyExecutionUpdateReqVO;
import com.yudao.module.strategy.convert.execution.StrategyExecutionConvert;
import com.yudao.module.strategy.dal.dataobject.execution.StrategyExecution;
import com.yudao.module.strategy.dal.mysql.execution.StrategyExecutionMapper;
import com.yudao.module.strategy.enums.StrategyExecutionStatusEnum;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static com.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.yudao.module.strategy.enums.ErrorCodeConstants.*;

/**
 * 策略执行 Service 实现类
 */
@Service
@Validated
public class StrategyExecutionServiceImpl implements StrategyExecutionService {

    @Resource
    private StrategyExecutionMapper strategyExecutionMapper;

    /**
     * 创建策略执行记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    @Override
    public Long createStrategyExecution(StrategyExecutionCreateReqVO createReqVO) {
        // 校验策略定义是否存在等前置条件 (根据实际业务补充)
        // ...

        StrategyExecution strategyExecution = StrategyExecutionConvert.INSTANCE.convert(createReqVO);
        strategyExecution.setStatus(StrategyExecutionStatusEnum.NOT_STARTED.getStatus()); // 初始状态为未开始
        strategyExecutionMapper.insert(strategyExecution);
        return strategyExecution.getId();
    }

    /**
     * 更新策略执行记录
     *
     * @param updateReqVO 更新信息
     */
    @Override
    public void updateStrategyExecution(StrategyExecutionUpdateReqVO updateReqVO) {
        validateStrategyExecutionExists(updateReqVO.getId());
        StrategyExecution updateObj = StrategyExecutionConvert.INSTANCE.convert(updateReqVO);
        strategyExecutionMapper.updateById(updateObj);
    }

    /**
     * 启动策略执行
     *
     * @param id 策略执行编号
     */
    @Override
    public void startStrategyExecution(Long id) {
        StrategyExecution execution = validateStrategyExecutionExists(id);
        if (!StrategyExecutionStatusEnum.NOT_STARTED.getStatus().equals(execution.getStatus()) &&
            !StrategyExecutionStatusEnum.PAUSED.getStatus().equals(execution.getStatus()) &&
            !StrategyExecutionStatusEnum.ERROR.getStatus().equals(execution.getStatus())) {
            throw exception(STRATEGY_EXECUTION_CANNOT_START, execution.getStatus());
        }
        // 实际启动逻辑，可能需要与交易引擎交互
        // ...
        StrategyExecution updateObj = new StrategyExecution();
        updateObj.setId(id);
        updateObj.setStatus(StrategyExecutionStatusEnum.RUNNING.getStatus());
        updateObj.setStartTime(LocalDateTime.now()); // 记录开始时间
        strategyExecutionMapper.updateById(updateObj);
    }

    /**
     * 暂停策略执行
     *
     * @param id 策略执行编号
     */
    @Override
    public void pauseStrategyExecution(Long id) {
        StrategyExecution execution = validateStrategyExecutionExists(id);
        if (!StrategyExecutionStatusEnum.RUNNING.getStatus().equals(execution.getStatus())) {
            throw exception(STRATEGY_EXECUTION_CANNOT_PAUSE, execution.getStatus());
        }
        // 实际暂停逻辑
        // ...
        StrategyExecution updateObj = new StrategyExecution();
        updateObj.setId(id);
        updateObj.setStatus(StrategyExecutionStatusEnum.PAUSED.getStatus());
        strategyExecutionMapper.updateById(updateObj);
    }

    /**
     * 停止策略执行
     *
     * @param id 策略执行编号
     */
    @Override
    public void stopStrategyExecution(Long id) {
        StrategyExecution execution = validateStrategyExecutionExists(id);
        if (StrategyExecutionStatusEnum.COMPLETED.getStatus().equals(execution.getStatus()) ||
            StrategyExecutionStatusEnum.STOPPED.getStatus().equals(execution.getStatus())) {
            throw exception(STRATEGY_EXECUTION_ALREADY_STOPPED_OR_COMPLETED, execution.getStatus());
        }
        // 实际停止逻辑，可能需要进行一些清理工作
        // ...
        StrategyExecution updateObj = new StrategyExecution();
        updateObj.setId(id);
        updateObj.setStatus(StrategyExecutionStatusEnum.STOPPED.getStatus());
        updateObj.setEndTime(LocalDateTime.now()); // 记录结束时间
        strategyExecutionMapper.updateById(updateObj);
    }

    /**
     * 删除策略执行记录
     *
     * @param id 编号
     */
    @Override
    public void deleteStrategyExecution(Long id) {
        validateStrategyExecutionExists(id);
        // 需要考虑是否允许删除运行中的策略等
        strategyExecutionMapper.deleteById(id);
    }

    /**
     * 校验策略执行记录是否存在
     *
     * @param id 策略执行ID
     * @return 策略执行记录
     */
    private StrategyExecution validateStrategyExecutionExists(Long id) {
        StrategyExecution execution = strategyExecutionMapper.selectById(id);
        if (execution == null) {
            throw exception(STRATEGY_EXECUTION_NOT_EXISTS);
        }
        return execution;
    }

    /**
     * 获得策略执行记录
     *
     * @param id 编号
     * @return 策略执行记录
     */
    @Override
    public StrategyExecution getStrategyExecution(Long id) {
        return strategyExecutionMapper.selectById(id);
    }

    /**
     * 获得策略执行记录列表
     *
     * @param ids 编号列表
     * @return 策略执行记录列表
     */
    @Override
    public List<StrategyExecution> getStrategyExecutionList(Collection<Long> ids) {
        return strategyExecutionMapper.selectBatchIds(ids);
    }

    /**
     * 获得策略执行记录分页
     *
     * @param pageReqVO 分页查询
     * @return 策略执行记录分页
     */
    @Override
    public PageResult<StrategyExecution> getStrategyExecutionPage(StrategyExecutionPageReqVO pageReqVO) {
        return strategyExecutionMapper.selectPage(pageReqVO, StrategyExecutionConvert.INSTANCE.convert(pageReqVO));
    }

    /**
     * 根据策略定义ID查询执行记录列表
     *
     * @param strategyDefinitionId 策略定义ID
     * @return 策略执行记录列表
     */
    @Override
    public List<StrategyExecution> getStrategyExecutionsByDefinitionId(Long strategyDefinitionId) {
        return strategyExecutionMapper.selectList(StrategyExecution::getStrategyDefinitionId, strategyDefinitionId);
    }
}