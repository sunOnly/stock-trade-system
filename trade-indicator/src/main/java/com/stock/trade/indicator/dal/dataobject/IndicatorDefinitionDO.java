package com.stock.trade.indicator.dal.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.stock.trade.framework.mybatis.core.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 技术指标定义 DO
 *
 * @author tianxin
 */
@TableName("indicator_definition")
@Data
@EqualsAndHashCode(callSuper = true)
public class IndicatorDefinitionDO extends BaseDO {

    /**
     * 指标ID，主键自增
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 指标名称，例如：Moving Average, MACD, RSI
     */
    private String name;

    /**
     * 指标简称，例如：MA, MACD, RSI
     */
    private String shortName;

    /**
     * 指标描述
     */
    private String description;

    /**
     * 指标类型，例如：趋势型、震荡型、成交量型
     * 可以使用枚举定义
     */
    private String type;

    /**
     * 计算公式描述或类引用
     * 例如："CLOSE / MA(CLOSE, N)" 或 "com.stock.trade.indicator.calculator.MACDCalculator"
     */
    private String formula;

    /**
     * 默认参数，JSON格式，例如：{"period": 20} for MA, {"shortPeriod":12, "longPeriod":26, "signalPeriod":9} for MACD
     */
    private String defaultParams;

    /**
     * 是否启用
     */
    private Boolean enabled;

    /**
     * 备注
     */
    private String remark;

}