package com.yudao.module.strategy.controller.admin.execution.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 策略执行 Response VO")
@Data
public class StrategyExecutionRespVO {

    @Schema(description = "执行记录ID", required = true, example = "1")
    private Long id;

    @Schema(description = "策略定义ID", required = true, example = "1024")
    private Long strategyDefinitionId;

    @Schema(description = "执行名称", required = true, example = "MA策略实盘执行-20240101")
    private String name;

    @Schema(description = "执行类型（1：实盘交易，2：模拟交易）", required = true, example = "1")
    private Integer type;

    @Schema(description = "初始资金", required = true, example = "100000.00")
    private BigDecimal initialCapital;

    @Schema(description = "当前资金", required = true, example = "105000.00")
    private BigDecimal currentCapital;

    @Schema(description = "总盈亏金额", required = true, example = "5000.00")
    private BigDecimal totalProfitLoss;

    @Schema(description = "总盈亏比例", required = true, example = "0.05")
    private BigDecimal totalProfitLossRatio;

    @Schema(description = "手续费率", required = true, example = "0.0003")
    private BigDecimal commissionRate;

    @Schema(description = "执行状态（0：未开始，1：运行中，2：已暂停，3：已停止，4：已完成，5：执行错误）", required = true, example = "1")
    private Integer status;

    @Schema(description = "错误信息", example = "策略执行异常：无法获取行情数据")
    private String errorMessage;

    @Schema(description = "开始执行时间", example = "2024-01-01 09:30:00")
    private LocalDateTime startTime;

    @Schema(description = "结束执行时间", example = "2024-01-01 15:00:00")
    private LocalDateTime endTime;

    @Schema(description = "执行参数（JSON格式）", example = "{\"stopLoss\": 0.05}")
    private String parameters;

    @Schema(description = "备注", example = "测试执行")
    private String remark;

    @Schema(description = "创建时间", required = true)
    private LocalDateTime createTime;

    @Schema(description = "更新时间", required = true)
    private LocalDateTime updateTime;

}