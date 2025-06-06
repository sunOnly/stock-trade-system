package com.stock.trade.order.enums;

import com.stock.trade.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 订单方向枚举
 *
 * @author xxx
 */
@Getter
@AllArgsConstructor
public enum OrderDirectionEnum implements IntArrayValuable {

    BUY(0, "买入"),
    SELL(1, "卖出");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(OrderDirectionEnum::getDirection).toArray();

    /**
     * 方向编码
     */
    private final Integer direction;
    /**
     * 方向描述
     */
    private final String desc;

    @Override
    public int[] array() {
        return ARRAYS;
    }
}