package com.stock.trade.indicator.service;

import com.stock.trade.framework.common.pojo.PageResult;
import com.stock.trade.indicator.controller.vo.indicator.value.*;
import com.stock.trade.indicator.dal.dataobject.IndicatorValueDO;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 技术指标计算结果 Service 接口
 *
 * @author AI Assistant
 */
public interface IndicatorValueService {

    /**
     * 创建指标计算结果
     *
     * @param createReqVO 创建信息
     * @return 指标计算结果ID
     */
    Long createIndicatorValue(@Valid IndicatorValueCreateReqVO createReqVO);

    /**
     * 更新指标计算结果
     *
     * @param updateReqVO 更新信息
     */
    void updateIndicatorValue(@Valid IndicatorValueUpdateReqVO updateReqVO);

    /**
     * 删除指标计算结果
     *
     * @param id 指标计算结果ID
     */
    void deleteIndicatorValue(Long id);

    /**
     * 获取指标计算结果
     *
     * @param id 指标计算结果ID
     * @return 指标计算结果
     */
    IndicatorValueDO getIndicatorValue(Long id);

    /**
     * 获取指标计算结果列表
     *
     * @param ids 指标计算结果ID列表
     * @return 指标计算结果列表
     */
    List<IndicatorValueDO> getIndicatorValueList(List<Long> ids);

    /**
     * 获取指标计算结果分页
     *
     * @param pageReqVO 分页查询
     * @return 指标计算结果分页
     */
    PageResult<IndicatorValueDO> getIndicatorValuePage(IndicatorValuePageReqVO pageReqVO);

    /**
     * 获取指标计算结果列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 指标计算结果列表
     */
    List<IndicatorValueDO> getIndicatorValueList(IndicatorValueExportReqVO exportReqVO);

    /**
     * 获取最新的指标计算结果
     *
     * @param indicatorDefinitionId 指标定义ID
     * @param stockCode 股票代码
     * @param period 时间周期
     * @return 指标计算结果
     */
    IndicatorValueDO getLatestIndicatorValue(Long indicatorDefinitionId, String stockCode, String period);

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
    List<IndicatorValueDO> getIndicatorValueListByTimeRange(Long indicatorDefinitionId, String stockCode,
            String period, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取指定状态的指标计算结果列表
     *
     * @param status 计算状态
     * @return 指标计算结果列表
     */
    List<IndicatorValueDO> getIndicatorValueListByStatus(String status);

    /**
     * 批量更新指标计算结果状态
     *
     * @param ids 指标计算结果ID列表
     * @param status 新状态
     * @param errorMessage 错误信息（可选）
     */
    void updateIndicatorValueStatusBatch(List<Long> ids, String status, String errorMessage);

    /**
     * 触发指标计算
     *
     * @param calculateReqVO 计算请求信息
     * @return 指标计算结果ID列表
     */
    List<Long> calculateIndicatorValue(@Valid IndicatorValueCalculateReqVO calculateReqVO);

}