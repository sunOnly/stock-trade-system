package com.stock.trade.order.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 订单日志 Response VO")
@Data
public class OrderLogRespVO {

    @Schema(description = "日志编号", required = true, example = "1024")
    private Long id;

    @Schema(description = "订单编号", required = true, example = "2048")
    private Long orderId;

    @Schema(description = "操作前订单状态，参见 OrderStatusEnum 枚举", required = true, example = "0")
    private Integer beforeStatus;

    @Schema(description = "操作后订单状态，参见 OrderStatusEnum 枚举", required = true, example = "1")
    private Integer afterStatus;

    @Schema(description = "操作内容", required = true, example = "创建订单")
    private String content;

    @Schema(description = "操作时间", required = true)
    private LocalDateTime operateTime;

    @Schema(description = "操作人编号", required = true, example = "1001")
    private Long operatorId;

    @Schema(description = "创建时间", required = true)
    private LocalDateTime createTime;

}
