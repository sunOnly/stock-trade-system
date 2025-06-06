package com.stock.trade.order.dal.dataobject;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.stock.trade.common.core.dataobject.BaseDO;
import com.stock.trade.order.enums.OrderDirectionEnum;
import com.stock.trade.order.enums.OrderStatusEnum;
import com.stock.trade.order.enums.OrderTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单 DO
 *
 * @author xxx
 */
@TableName("trade_order")
@KeySequence("trade_order_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrderDO extends BaseDO {

    /**
     * 订单编号，主键自增
     */
    @TableId
    private Long id;
    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 股票代码
     */
    private String stockCode;
    /**
     * 股票名称
     */
    private String stockName;
    /**
     * 订单类型
     *
     * 枚举 {@link OrderTypeEnum}
     */
    private Integer type;
    /**
     * 订单方向
     *
     * 枚举 {@link OrderDirectionEnum}
     */
    private Integer direction;
    /**
     * 订单价格
     */
    private BigDecimal price;
    /**
     * 订单数量
     */
    private Integer quantity;
    /**
     * 已成交数量
     */
    private Integer filledQuantity;
    /**
     * 订单状态
     *
     * 枚举 {@link OrderStatusEnum}
     */
    private Integer status;
    /**
     * 订单时间
     */
    private LocalDateTime orderTime;
    /**
     * 成交均价
     */
    private BigDecimal avgFillPrice;
    /**
     * 备注
     */
    private String remark;

}