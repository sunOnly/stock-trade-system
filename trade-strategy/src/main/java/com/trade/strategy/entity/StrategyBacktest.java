package com.trade.strategy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 策略回测记录实体类
 *
 * @author AI Assistant
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ts_strategy_backtest")
public class StrategyBacktest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 回测ID (主键, 自增)
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 关联的策略定义ID
     */
    private Long strategyDefinitionId;

    /**
     * 回测名称 (用户自定义)
     */
    private String backtestName;

    /**
     * 回测开始日期
     */
    private LocalDate startDate;

    /**
     * 回测结束日期
     */
    private LocalDate endDate;

    /**
     * 初始资金
     */
    private BigDecimal initialCapital;

    /**
     * 手续费率 (例如: 0.0003 表示万分之三)
     */
    private BigDecimal commissionRate;

    /**
     * 回测状态 (例如: "PENDING", "RUNNING", "COMPLETED", "FAILED")
     * 可以考虑使用枚举类定义
     */
    private String status;

    /**
     * 回测结果 (JSON格式，存储回测的各项指标)
     * 例如: {"total_return": 0.15, "annualized_return": 0.30, "sharpe_ratio": 1.5, "max_drawdown": 0.10}
     */
    private String resultDetails; // 数据库中建议使用TEXT/JSON类型

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间 (例如回测完成或失败时更新)
     */
    private LocalDateTime updateTime;

}