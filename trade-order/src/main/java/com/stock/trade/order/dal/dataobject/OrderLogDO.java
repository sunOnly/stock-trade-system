package com.stock.trade.order.dal.dataobject;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.stock.trade.common.core.dataobject.BaseDO;
import com.stock.trade.order.enums.OrderStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 订单日志 DO
 *
 * @author xxx
 */
@TableName("trade_order_log")
@KeySequence("trade_order_log_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrderLogDO extends BaseDO {

    /**
     * 日志编号，主键自增
     */
    @TableId
    private Long id;
    /**
     * 订单编号，关联 {@link OrderDO#getId()}
     */
    private Long orderId;
    /**
     * 操作前订单状态
     *
     * 枚举 {@link OrderStatusEnum}
     */
    private Integer beforeStatus;
    /**
     * 操作后订单状态
     *
     * 枚举 {@link OrderStatusEnum}
     */
    private Integer afterStatus;
    /**
     * 操作内容
     */
    private String content;
    /**
     * 操作时间
     */
    private LocalDateTime operationTime;
    /**
     * 操作人编号 (系统操作时，可以为空)
     */
    private Long operatorUserId;

}