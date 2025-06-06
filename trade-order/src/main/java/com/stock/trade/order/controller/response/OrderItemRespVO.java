package com.stock.trade.order.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 订单项 Response VO")
@Data
public class OrderItemRespVO {

    @Schema(description = "订单项编号", required = true, example = "1024")
    private Long id;

    @Schema(description = "订单编号", required = true, example = "2048")
    private Long orderId;

    @Schema(description = "成交编号", required = true, example = "T202305200001")
    private String tradeNo;

    @Schema(description = "成交价格", required = true, example = "10.24")
    private BigDecimal price;

    @Schema(description = "成交数量", required = true, example = "100")
    private Integer quantity;

    @Schema(description = "成交时间", required = true)
    private LocalDateTime tradeTime;

    @Schema(description = "交易费用", required = true, example = "5.12")
    private BigDecimal fee;

    @Schema(description = "创建时间", required = true)
    private LocalDateTime createTime;

}