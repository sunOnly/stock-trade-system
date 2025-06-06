package com.stock.trade.indicator.dal.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.stock.trade.framework.mybatis.core.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 技术指标计算结果 DO
 *
 * @author tianxin
 */
@TableName("indicator_value")
@Data
@EqualsAndHashCode(callSuper = true)
public class IndicatorValueDO extends BaseDO {

    /**
     * 指标值ID，主键自增
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 关联的指标定义ID
     * {@link IndicatorDefinitionDO#getId()}
     */
    private Long definitionId;

    /**
     * 股票代码
     */
    private String stockCode;

    /**
     * 时间周期，例如：1min, 5min, 1day, 1week
     */
    private String timePeriod;

    /**
     * 计算时间点
     */
    private LocalDateTime calculationTime;

    /**
     * 指标计算参数，JSON格式，覆盖或补充指标定义的默认参数
     * 例如：{"period": 30}
     */
    private String params;

    /**
     * 指标计算结果，JSON格式
     * 例如：{"ma": 10.5} 或 {"macd": 0.5, "diff": 0.2, "dea": 0.3}
     */
    private String results;

    /**
     * 计算状态，例如：PENDING, CALCULATING, SUCCESS, FAILED
     * 可以使用枚举定义
     */
    private String status;

    /**
     * 错误信息，当计算失败时记录
     */
    private String errorMessage;

    /**
     * 备注
     */
    private String remark;

}