package com.stock.trade.order.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 订单更新 Request VO")
@Data
public class OrderUpdateReqVO {

    @Schema(description = "订单编号", required = true, example = "1024")
    @NotNull(message = "订单编号不能为空")
    private Long id;

    @Schema(description = "订单价格，市价单可为空", example = "10.24")
    private BigDecimal price;

    @Schema(description = "订单数量", example = "100")
    private Integer quantity;

    @Schema(description = "备注", example = "修改订单")
    private String remark;

}