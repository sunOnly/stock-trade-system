package com.stock.trade.order.mq.message;

import com.stock.trade.common.mq.message.AbstractStreamMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderMessage extends AbstractStreamMessage {

    public static final String STREAM_KEY = "stock.trade.order.change";

    /**
     * 订单编号
     */
    @NotNull(message = "订单编号不能为空")
    private Long orderId;

    /**
     * 用户编号
     */
    @NotNull(message = "用户编号不能为空")
    private Long userId;

    /**
     * 订单状态
     */
    @NotNull(message = "订单状态不能为空")
    private Integer status;

    @Override
    public String getStreamKey() {
        return STREAM_KEY;
    }
}