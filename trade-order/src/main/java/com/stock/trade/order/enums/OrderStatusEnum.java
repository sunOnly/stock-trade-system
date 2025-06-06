package com.stock.trade.order.enums;

import com.stock.trade.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 订单状态枚举
 *
 * @author xxx
 */
@Getter
@AllArgsConstructor
public enum OrderStatusEnum implements IntArrayValuable {

    PENDING_NEW(0, "待报"), // 订单已创建，但尚未发送到交易所
    NEW(1, "已报"),         // 订单已发送到交易所，等待撮合
    PARTIALLY_FILLED(2, "部分成交"),
    FILLED(3, "全部成交"),
    CANCELED(4, "已撤销"),
    REJECTED(5, "已拒绝"),   // 订单被交易所拒绝
    EXPIRED(6, "已过期");    // 订单因过期未成交而失效

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(OrderStatusEnum::getStatus).toArray();

    /**
     * 状态编码
     */
    private final Integer status;
    /**
     * 状态描述
     */
    private final String desc;

    @Override
    public int[] array() {
        return ARRAYS;
    }

    /**
     * 判断订单是否为最终状态 (不可再变更)
     *
     * @param status 订单状态
     * @return 是否为最终状态
     */
    public static boolean isFinalStatus(Integer status) {
        return FILLED.getStatus().equals(status)
                || CANCELED.getStatus().equals(status)
                || REJECTED.getStatus().equals(status)
                || EXPIRED.getStatus().equals(status);
    }

    /**
     * 判断订单是否可以被撤销
     *
     * @param status 订单状态
     * @return 是否可以被撤销
     */
    public static boolean canCancel(Integer status) {
        return PENDING_NEW.getStatus().equals(status)
                || NEW.getStatus().equals(status)
                || PARTIALLY_FILLED.getStatus().equals(status);
    }
}