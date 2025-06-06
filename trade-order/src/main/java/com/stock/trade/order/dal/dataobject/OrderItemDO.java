package com.stock.trade.order.dal.dataobject;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.stock.trade.common.core.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单项 DO
 *
 * @author xxx
 */
@TableName("trade_order_item")
@KeySequence("trade_order_item_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrderItemDO extends BaseDO {

    /**
     * 订单项编号，主键自增
     */
    @TableId
    private Long id;
    /**
     * 订单编号，关联 {@link OrderDO#getId()}
     */
    private Long orderId;
    /**
     * 成交编号 (如果部分成交，一个订单可能会有多条成交记录)
     */
    private String tradeNo;
    /**
     * 成交价格
     */
    private BigDecimal fillPrice;
    /**
     * 成交数量
     */
    private Integer fillQuantity;
    /**
     * 成交时间
     */
    private LocalDateTime fillTime;
    /**
     * 交易费用
     */
    private BigDecimal commission;

}