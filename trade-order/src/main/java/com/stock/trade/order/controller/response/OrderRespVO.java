package com.stock.trade.order.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 订单 Response VO")
@Data
public class OrderRespVO {

    @Schema(description = "订单编号", required = true, example = "1024")
    private Long id;

    @Schema(description = "用户编号", required = true, example = "1024")
    private Long userId;

    @Schema(description = "股票代码", required = true, example = "600000")
    private String stockCode;

    @Schema(description = "股票名称", required = true, example = "浦发银行")
    private String stockName;

    @Schema(description = "订单类型，参见 OrderTypeEnum 枚举", required = true, example = "0")
    private Integer type;

    @Schema(description = "订单方向，参见 OrderDirectionEnum 枚举", required = true, example = "0")
    private Integer direction;

    @Schema(description = "订单价格", example = "10.24")
    private BigDecimal price;

    @Schema(description = "订单数量", required = true, example = "100")
    private Integer quantity;

    @Schema(description = "已成交数量", required = true, example = "50")
    private Integer filledQuantity;

    @Schema(description = "订单状态，参见 OrderStatusEnum 枚举", required = true, example = "0")
    private Integer status;

    @Schema(description = "订单时间", required = true)
    private LocalDateTime orderTime;

    @Schema(description = "成交均价", example = "10.24")
    private BigDecimal avgFillPrice;

    @Schema(description = "备注", example = "测试订单")
    private String remark;

    @Schema(description = "创建时间", required = true)
    private LocalDateTime createTime;

}