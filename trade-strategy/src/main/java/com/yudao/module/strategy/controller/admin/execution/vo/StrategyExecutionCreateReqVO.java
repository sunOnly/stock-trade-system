package com.yudao.module.strategy.controller.admin.execution.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 策略执行创建 Request VO")
@Data
public class StrategyExecutionCreateReqVO {

    @Schema(description = "策略定义ID", required = true, example = "1024")
    @NotNull(message = "策略定义ID不能为空")
    private Long strategyDefinitionId;

    @Schema(description = "执行名称", required = true, example = "MA策略实盘执行-20240101")
    @NotEmpty(message = "执行名称不能为空")
    private String name;

    @Schema(description = "执行类型（1：实盘交易，2：模拟交易）", required = true, example = "1")
    @NotNull(message = "执行类型不能为空")
    private Integer type;

    @Schema(description = "初始资金", required = true, example = "100000.00")
    @NotNull(message = "初始资金不能为空")
    private BigDecimal initialCapital;

    @Schema(description = "手续费率", required = true, example = "0.0003")
    @NotNull(message = "手续费率不能为空")
    private BigDecimal commissionRate;

    @Schema(description = "执行参数（JSON格式）", example = "{\"stopLoss\": 0.05}")
    private String parameters;

    @Schema(description = "备注", example = "测试执行")
    private String remark;

}