package com.stock.trade.order.controller.request;

import com.stock.trade.order.enums.OrderDirectionEnum;
import com.stock.trade.order.enums.OrderTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 订单创建 Request VO")
@Data
public class OrderCreateReqVO {

    @Schema(description = "用户编号", required = true, example = "1024")
    @NotNull(message = "用户编号不能为空")
    private Long userId;

    @Schema(description = "股票代码", required = true, example = "600000")
    @NotEmpty(message = "股票代码不能为空")
    private String stockCode;

    @Schema(description = "股票名称", required = true, example = "浦发银行")
    @NotEmpty(message = "股票名称不能为空")
    private String stockName;

    @Schema(description = "订单类型，参见 OrderTypeEnum 枚举", required = true, example = "0")
    @NotNull(message = "订单类型不能为空")
    private Integer type;

    @Schema(description = "订单方向，参见 OrderDirectionEnum 枚举", required = true, example = "0")
    @NotNull(message = "订单方向不能为空")
    private Integer direction;

    @Schema(description = "订单价格，市价单可为空", example = "10.24")
    private BigDecimal price;

    @Schema(description = "订单数量", required = true, example = "100")
    @NotNull(message = "订单数量不能为空")
    private Integer quantity;

    @Schema(description = "备注", example = "测试订单")
    private String remark;

}