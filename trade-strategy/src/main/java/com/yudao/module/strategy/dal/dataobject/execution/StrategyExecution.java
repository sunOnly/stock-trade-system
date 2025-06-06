package com.yudao.module.strategy.dal.dataobject.execution;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 策略执行记录 DO
 */
@TableName("strategy_execution")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StrategyExecution extends BaseDO {

    /**
     * 执行记录ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 策略定义ID
     */
    private Long strategyDefinitionId;

    /**
     * 执行名称
     */
    private String name;

    /**
     * 执行类型（1：实盘交易，2：模拟交易）
     */
    private Integer type;

    /**
     * 初始资金
     */
    private BigDecimal initialCapital;

    /**
     * 当前资金
     */
    private BigDecimal currentCapital;

    /**
     * 总盈亏金额
     */
    private BigDecimal totalProfitLoss;

    /**
     * 总盈亏比例
     */
    private BigDecimal totalProfitLossRatio;

    /**
     * 手续费率
     */
    private BigDecimal commissionRate;

    /**
     * 执行状态（0：未开始，1：运行中，2：已暂停，3：已停止，4：已完成，5：执行错误）
     */
    private Integer status;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 开始执行时间
     */
    private LocalDateTime startTime;

    /**
     * 结束执行时间
     */
    private LocalDateTime endTime;

    /**
     * 执行参数（JSON格式）
     */
    private String parameters;

    /**
     * 备注
     */
    private String remark;
}