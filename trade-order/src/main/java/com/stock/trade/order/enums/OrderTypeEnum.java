package com.stock.trade.order.enums;

import com.stock.trade.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 订单类型枚举
 *
 * @author xxx
 */
@Getter
@AllArgsConstructor
public enum OrderTypeEnum implements IntArrayValuable {

    LIMIT(0, "限价单"),
    MARKET(1, "市价单"),
    STOP(2, "止损单"),
    STOP_LIMIT(3, "止损限价单");
    // TODO 后续可以根据实际需求扩展更多订单类型，例如 FOK, FAK 等

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(OrderTypeEnum::getType).toArray();

    /**
     * 类型编码
     */
    private final Integer type;
    /**
     * 类型描述
     */
    private final String desc;

    @Override
    public int[] array() {
        return ARRAYS;
    }
}