package com.stock.trade.indicator.service.impl;

import com.stock.trade.framework.common.pojo.PageResult;
import com.stock.trade.indicator.controller.vo.indicator.value.*;
import com.stock.trade.indicator.convert.IndicatorValueConvert;
import com.stock.trade.indicator.dal.dataobject.IndicatorValueDO;
import com.stock.trade.indicator.dal.mysql.IndicatorValueMapper;
import com.stock.trade.indicator.service.IndicatorDefinitionService;
import com.stock.trade.indicator.service.IndicatorValueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static com.stock.trade.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.stock.trade.indicator.enums.ErrorCodeConstants.*;

/**
 * 技术指标计算结果 Service 实现类
 *
 * @author AI Assistant
 */
@Service
@Validated
@Slf4j
public class IndicatorValueServiceImpl implements IndicatorValueService {

    @Resource
    private IndicatorValueMapper indicatorValueMapper;

    @Resource
    private IndicatorDefinitionService indicatorDefinitionService; // 用于校验指标定义是否存在等

    /**
     * 创建指标计算结果
     *
     * @param createReqVO 创建信息
     * @return 指标计算结果ID
     */
    @Override
    public Long createIndicatorValue(IndicatorValueCreateReqVO createReqVO) {
        // 校验指标定义是否存在
        validateIndicatorDefinitionExists(createReqVO.getDefinitionId());

        IndicatorValueDO indicatorValue = IndicatorValueConvert.INSTANCE.convert(createReqVO);
        indicatorValueMapper.insert(indicatorValue);
        return indicatorValue.getId();
    }

    /**
     * 更新指标计算结果
     *
     * @param updateReqVO 更新信息
     */
    @Override
    public void updateIndicatorValue(IndicatorValueUpdateReqVO updateReqVO) {
        // 校验存在
        validateIndicatorValueExists(updateReqVO.getId());
        // 校验指标定义是否存在
        validateIndicatorDefinitionExists(updateReqVO.getDefinitionId());

        IndicatorValueDO updateObj = IndicatorValueConvert.INSTANCE.convert(updateReqVO);
        indicatorValueMapper.updateById(updateObj);
    }

    /**
     * 删除指标计算结果
     *
     * @param id 指标计算结果ID
     */
    @Override
    public void deleteIndicatorValue(Long id) {
        // 校验存在
        validateIndicatorValueExists(id);
        indicatorValueMapper.deleteById(id);
    }

    /**
     * 获取指标计算结果
     *
     * @param id 指标计算结果ID
     * @return 指标计算结果
     */
    @Override
    public IndicatorValueDO getIndicatorValue(Long id) {
        return indicatorValueMapper.selectById(id);
    }

    /**
     * 获取指标计算结果列表
     *
     * @param ids 指标计算结果ID列表
     * @return 指标计算结果列表
     */
    @Override
    public List<IndicatorValueDO> getIndicatorValueList(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }
        return indicatorValueMapper.selectBatchIds(ids);
    }

    /**
     * 获取指标计算结果分页
     *
     * @param pageReqVO 分页查询
     * @return 指标计算结果分页
     */
    @Override
    public PageResult<IndicatorValueDO> getIndicatorValuePage(IndicatorValuePageReqVO pageReqVO) {
        return indicatorValueMapper.selectPage(pageReqVO, IndicatorValueConvert.INSTANCE.convert(pageReqVO));
    }

    /**
     * 获取指标计算结果列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 指标计算结果列表
     */
    @Override
    public List<IndicatorValueDO> getIndicatorValueList(IndicatorValueExportReqVO exportReqVO) {
        return indicatorValueMapper.selectList(IndicatorValueConvert.INSTANCE.convert(exportReqVO));
    }

    /**
     * 获取最新的指标计算结果
     *
     * @param indicatorDefinitionId 指标定义ID
     * @param stockCode 股票代码
     * @param period 时间周期
     * @return 指标计算结果
     */
    @Override
    public IndicatorValueDO getLatestIndicatorValue(Long indicatorDefinitionId, String stockCode, String period) {
        // 校验指标定义是否存在
        validateIndicatorDefinitionExists(indicatorDefinitionId);
        return indicatorValueMapper.selectLatest(indicatorDefinitionId, stockCode, period);
    }

    /**
     * 获取指定时间范围内的指标计算结果列表
     *
     * @param indicatorDefinitionId 指标定义ID
     * @param stockCode 股票代码
     * @param period 时间周期
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 指标计算结果列表
     */
    @Override
    public List<IndicatorValueDO> getIndicatorValueListByTimeRange(Long indicatorDefinitionId, String stockCode, String period, LocalDateTime startTime, LocalDateTime endTime) {
        // 校验指标定义是否存在
        validateIndicatorDefinitionExists(indicatorDefinitionId);
        return indicatorValueMapper.selectListByTimeRange(indicatorDefinitionId, stockCode, period, startTime, endTime);
    }

    /**
     * 获取指定状态的指标计算结果列表
     *
     * @param status 计算状态
     * @return 指标计算结果列表
     */
    @Override
    public List<IndicatorValueDO> getIndicatorValueListByStatus(String status) {
        return indicatorValueMapper.selectListByStatus(status);
    }

    /**
     * 批量更新指标计算结果状态
     *
     * @param ids 指标计算结果ID列表
     * @param status 新状态
     * @param errorMessage 错误信息（可选）
     */
    @Override
    @Transactional
    public void updateIndicatorValueStatusBatch(List<Long> ids, String status, String errorMessage) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        indicatorValueMapper.updateStatusBatch(ids, status, errorMessage);
    }

    /**
     * 触发指标计算
     *
     * @param calculateReqVO 计算请求信息
     * @return 指标计算结果ID列表 (这里仅为示例，实际计算逻辑可能更复杂，并可能异步执行)
     */
    @Override
    public List<Long> calculateIndicatorValue(IndicatorValueCalculateReqVO calculateReqVO) {
        // 校验指标定义是否存在
        validateIndicatorDefinitionExists(calculateReqVO.getDefinitionId());

        // 实际的指标计算逻辑会在这里实现，可能涉及调用行情数据、计算引擎等
        // 此处仅为示例，创建一个虚拟的计算结果并返回ID
        log.info("触发指标计算: {}, 股票: {}, 周期: {}, 参数: {}",
                calculateReqVO.getDefinitionId(), calculateReqVO.getStockCode(),
                calculateReqVO.getPeriod(), calculateReqVO.getParams());

        // 模拟创建计算结果
        IndicatorValueCreateReqVO createReq = new IndicatorValueCreateReqVO();
        createReq.setDefinitionId(calculateReqVO.getDefinitionId());
        createReq.setStockCode(calculateReqVO.getStockCode());
        createReq.setTimePeriod(calculateReqVO.getPeriod());
        createReq.setCalculationTime(LocalDateTime.now());
        createReq.setParams("{ \"info\": \"triggered by calculateIndicatorValue\" }"); // 示例参数
        createReq.setValues("{ \"MA5\": 10.5, \"MA10\": 10.2 }"); // 示例结果
        createReq.setStatus("PENDING"); // 初始状态为待计算或计算中

        Long valueId = createIndicatorValue(createReq);
        return Collections.singletonList(valueId);
    }

    /**
     * 校验指标计算结果是否存在
     *
     * @param id 指标计算结果ID
     */
    private void validateIndicatorValueExists(Long id) {
        if (indicatorValueMapper.selectById(id) == null) {
            throw exception(INDICATOR_VALUE_NOT_EXISTS);
        }
    }

    /**
     * 校验关联的技术指标定义是否存在
     *
     * @param definitionId 指标定义ID
     */
    private void validateIndicatorDefinitionExists(Long definitionId) {
        if (indicatorDefinitionService.getIndicatorDefinition(definitionId) == null) {
            throw exception(INDICATOR_DEFINITION_NOT_EXISTS);
        }
    }
}