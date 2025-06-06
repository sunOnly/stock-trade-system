package com.stock.trade.order.controller.request;

import com.stock.trade.common.core.dal.qo.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 订单分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrderPageReqVO extends PageQuery {

    @Schema(description = "用户编号", example = "1024")
    private Long userId;

    @Schema(description = "股票代码", example = "600000")
    private String stockCode;

    @Schema(description = "订单类型，参见 OrderTypeEnum 枚举", example = "0")
    private Integer type;

    @Schema(description = "订单方向，参见 OrderDirectionEnum 枚举", example = "0")
    private Integer direction;

    @Schema(description = "订单状态，参见 OrderStatusEnum 枚举", example = "0")
    private Integer status;

}