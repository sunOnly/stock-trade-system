package com.yudao.module.strategy.controller.admin.execution.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 策略执行更新 Request VO")
@Data
public class StrategyExecutionUpdateReqVO {

    @Schema(description = "执行记录ID", required = true, example = "1")
    @NotNull(message = "执行记录ID不能为空")
    private Long id;

    @Schema(description = "执行名称", example = "MA策略实盘执行-20240101-V2")
    private String name;

    @Schema(description = "执行类型（1：实盘交易，2：模拟交易）", example = "1")
    private Integer type;

    @Schema(description = "初始资金", example = "120000.00")
    private BigDecimal initialCapital;

    @Schema(description = "手续费率", example = "0.00025")
    private BigDecimal commissionRate;

    @Schema(description = "执行参数（JSON格式）", example = "{\"stopLoss\": 0.06, \"takeProfit\": 0.1}")
    private String parameters;

    @Schema(description = "备注", example = "更新执行参数")
    private String remark;

    // 注意：通常不直接通过API更新执行状态、当前资金、盈亏等，这些应由系统内部逻辑管理
    // 如果确实需要手动调整状态（例如管理员干预），可以单独提供接口或在此VO中添加并谨慎处理
}