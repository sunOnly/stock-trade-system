package com.stock.trade.indicator.dal.mysql;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.stock.trade.framework.mybatis.core.mapper.BaseMapperX;
import com.stock.trade.indicator.dal.dataobject.IndicatorValueDO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 技术指标计算结果 Mapper
 *
 * @author tianxin
 */
@Mapper
public interface IndicatorValueMapper extends BaseMapperX<IndicatorValueDO> {

    /**
     * 根据指标定义ID和股票代码查询最新的计算结果
     *
     * @param definitionId 指标定义ID
     * @param stockCode 股票代码
     * @return 最新的计算结果
     */
    default IndicatorValueDO selectLatestByDefinitionIdAndStockCode(Long definitionId, String stockCode) {
        return selectOne(new LambdaQueryWrapper<IndicatorValueDO>()
                .eq(IndicatorValueDO::getDefinitionId, definitionId)
                .eq(IndicatorValueDO::getStockCode, stockCode)
                .orderByDesc(IndicatorValueDO::getCalculationTime)
                .last("LIMIT 1"));
    }

    /**
     * 查询指定时间范围内的计算结果
     *
     * @param definitionId 指标定义ID
     * @param stockCode 股票代码
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 计算结果列表
     */
    default List<IndicatorValueDO> selectListByTimeRange(Long definitionId, String stockCode,
                                                        LocalDateTime startTime, LocalDateTime endTime) {
        return selectList(new LambdaQueryWrapper<IndicatorValueDO>()
                .eq(IndicatorValueDO::getDefinitionId, definitionId)
                .eq(IndicatorValueDO::getStockCode, stockCode)
                .between(IndicatorValueDO::getCalculationTime, startTime, endTime)
                .orderByAsc(IndicatorValueDO::getCalculationTime));
    }

    /**
     * 查询指定状态的计算结果
     *
     * @param status 计算状态
     * @return 计算结果列表
     */
    default List<IndicatorValueDO> selectListByStatus(String status) {
        return selectList(new LambdaQueryWrapper<IndicatorValueDO>()
                .eq(IndicatorValueDO::getStatus, status));
    }

    /**
     * 批量更新指标计算结果的状态
     *
     * @param ids 指标值ID列表
     * @param status 新状态
     * @return 更新的记录数
     */
    default int updateStatusBatch(List<Long> ids, String status) {
        return update(null, new LambdaUpdateWrapper<IndicatorValueDO>()
                .in(IndicatorValueDO::getId, ids)
                .set(IndicatorValueDO::getStatus, status));
    }

}