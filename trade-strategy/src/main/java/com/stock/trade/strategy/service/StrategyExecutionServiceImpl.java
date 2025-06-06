package com.stock.trade.strategy.service;

import com.stock.trade.framework.common.exception.util.ServiceExceptionUtil;
import com.stock.trade.framework.common.pojo.PageResult;
import com.stock.trade.strategy.controller.vo.StrategyExecutionCreateReqVO;
import com.stock.trade.strategy.controller.vo.StrategyExecutionPageReqVO;
import com.stock.trade.strategy.controller.vo.StrategyExecutionUpdateReqVO;
import com.stock.trade.strategy.convert.StrategyExecutionConvert;
import com.stock.trade.strategy.dal.dataobject.StrategyExecutionDO;
import com.stock.trade.strategy.dal.mysql.StrategyExecutionMapper;
import com.stock.trade.strategy.enums.ErrorCodeConstants;
import com.stock.trade.strategy.enums.StrategyExecutionStatusEnum;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.stock.trade.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 策略执行 Service 实现类
 *
 * @author AI Assistant
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
        // 插入
        StrategyExecutionDO strategyExecution = StrategyExecutionConvert.INSTANCE.convert(createReqVO);
        strategyExecution.setStatus(StrategyExecutionStatusEnum.PENDING.getStatus());
        strategyExecution.setCreateTime(LocalDateTime.now());
        strategyExecution.setUpdateTime(LocalDateTime.now());
        strategyExecutionMapper.insert(strategyExecution);
        // 返回
        return strategyExecution.getId();
    }

    /**
     * 更新策略执行记录
     *
     * @param updateReqVO 更新信息
     */
    @Override
    public void updateStrategyExecution(StrategyExecutionUpdateReqVO updateReqVO) {
        // 校验存在
        validateStrategyExecutionExists(updateReqVO.getId());
        // 更新
        StrategyExecutionDO updateDO = StrategyExecutionConvert.INSTANCE.convert(updateReqVO);
        updateDO.setUpdateTime(LocalDateTime.now());
        strategyExecutionMapper.updateById(updateDO);
    }

    /**
     * 启动策略执行
     *
     * @param id 策略执行ID
     */
    @Override
    public void startStrategyExecution(Long id) {
        StrategyExecutionDO execution = validateStrategyExecutionExists(id);
        if (!Objects.equals(execution.getStatus(), StrategyExecutionStatusEnum.PENDING.getStatus()) &&
            !Objects.equals(execution.getStatus(), StrategyExecutionStatusEnum.PAUSED.getStatus())) {
            throw exception(ErrorCodeConstants.STRATEGY_EXECUTION_STATUS_CANNOT_START);
        }
        StrategyExecutionDO updateDO = new StrategyExecutionDO();
        updateDO.setId(id);
        updateDO.setStatus(StrategyExecutionStatusEnum.RUNNING.getStatus());
        updateDO.setStartTime(LocalDateTime.now()); // 记录启动时间
        updateDO.setUpdateTime(LocalDateTime.now());
        strategyExecutionMapper.updateById(updateDO);
        // 此处应有实际的策略启动逻辑，例如与交易API交互，发送到执行引擎等
    }

    /**
     * 暂停策略执行
     *
     * @param id 策略执行ID
     */
    @Override
    public void pauseStrategyExecution(Long id) {
        StrategyExecutionDO execution = validateStrategyExecutionExists(id);
        if (!Objects.equals(execution.getStatus(), StrategyExecutionStatusEnum.RUNNING.getStatus())) {
            throw exception(ErrorCodeConstants.STRATEGY_EXECUTION_STATUS_CANNOT_PAUSE);
        }
        StrategyExecutionDO updateDO = new StrategyExecutionDO();
        updateDO.setId(id);
        updateDO.setStatus(StrategyExecutionStatusEnum.PAUSED.getStatus());
        updateDO.setUpdateTime(LocalDateTime.now());
        strategyExecutionMapper.updateById(updateDO);
        // 此处应有实际的策略暂停逻辑
    }

    /**
     * 停止策略执行
     *
     * @param id 策略执行ID
     */
    @Override
    public void stopStrategyExecution(Long id) {
        StrategyExecutionDO execution = validateStrategyExecutionExists(id);
        if (Objects.equals(execution.getStatus(), StrategyExecutionStatusEnum.COMPLETED.getStatus()) ||
            Objects.equals(execution.getStatus(), StrategyExecutionStatusEnum.FAILED.getStatus()) ||
            Objects.equals(execution.getStatus(), StrategyExecutionStatusEnum.STOPPED.getStatus())) {
            throw exception(ErrorCodeConstants.STRATEGY_EXECUTION_STATUS_CANNOT_STOP);
        }
        StrategyExecutionDO updateDO = new StrategyExecutionDO();
        updateDO.setId(id);
        updateDO.setStatus(StrategyExecutionStatusEnum.STOPPED.getStatus());
        updateDO.setEndTime(LocalDateTime.now()); // 记录停止时间
        updateDO.setUpdateTime(LocalDateTime.now());
        strategyExecutionMapper.updateById(updateDO);
        // 此处应有实际的策略停止逻辑
    }

    /**
     * 删除策略执行记录
     *
     * @param id 编号
     */
    @Override
    public void deleteStrategyExecution(Long id) {
        // 校验存在
        StrategyExecutionDO execution = validateStrategyExecutionExists(id);
        // 校验是否运行中
        if (Objects.equals(execution.getStatus(), StrategyExecutionStatusEnum.RUNNING.getStatus())) {
            throw exception(ErrorCodeConstants.STRATEGY_EXECUTION_CANNOT_DELETE_WHEN_RUNNING);
        }
        // 删除
        strategyExecutionMapper.deleteById(id);
    }

    /**
     * 校验策略执行记录是否存在
     *
     * @param id 策略执行ID
     * @return 策略执行实体
     */
    private StrategyExecutionDO validateStrategyExecutionExists(Long id) {
        StrategyExecutionDO execution = strategyExecutionMapper.selectById(id);
        if (execution == null) {
            throw exception(ErrorCodeConstants.STRATEGY_EXECUTION_NOT_EXISTS);
        }
        return execution;
    }

    /**
     * 获取策略执行记录
     *
     * @param id 编号
     * @return 策略执行记录
     */
    @Override
    public StrategyExecutionDO getStrategyExecution(Long id) {
        return strategyExecutionMapper.selectById(id);
    }

    /**
     * 获取策略执行记录列表
     *
     * @param ids 编号列表
     * @return 策略执行记录列表
     */
    @Override
    public List<StrategyExecutionDO> getStrategyExecutionList(List<Long> ids) {
        return strategyExecutionMapper.selectBatchIds(ids);
    }

    /**
     * 获取策略执行记录分页
     *
     * @param pageReqVO 分页查询
     * @return 策略执行记录分页
     */
    @Override
    public PageResult<StrategyExecutionDO> getStrategyExecutionPage(StrategyExecutionPageReqVO pageReqVO) {
        return strategyExecutionMapper.selectPage(pageReqVO);
    }

    /**
     * 根据策略定义ID获取策略执行记录列表
     *
     * @param strategyDefinitionId 策略定义ID
     * @return 策略执行记录列表
     */
    @Override
    public List<StrategyExecutionDO> getStrategyExecutionListByDefinitionId(Long strategyDefinitionId) {
        return strategyExecutionMapper.selectListByStrategyDefinitionId(strategyDefinitionId);
    }

}