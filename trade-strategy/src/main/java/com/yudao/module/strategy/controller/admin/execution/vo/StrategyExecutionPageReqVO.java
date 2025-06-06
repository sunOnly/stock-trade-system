package com.yudao.module.strategy.controller.admin.execution.vo;

import com.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static com.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 策略执行分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StrategyExecutionPageReqVO extends PageParam {

    @Schema(description = "策略定义ID", example = "1024")
    private Long strategyDefinitionId;

    @Schema(description = "执行名称", example = "MA策略")
    private String name;

    @Schema(description = "执行类型（1：实盘交易，2：模拟交易）", example = "1")
    private Integer type;

    @Schema(description = "执行状态（0：未开始，1：运行中，2：已暂停，3：已停止，4：已完成，5：执行错误）", example = "1")
    private Integer status;

    @Schema(description = "开始执行时间范围 - 开始")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] startTime;

    @Schema(description = "创建时间范围 - 开始")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}