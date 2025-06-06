package com.trade.marketdata.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 股票实时行情数据实体类
 *
 * @author Trae
 * @since 2024-07-26
 */
@Data
@TableName("realtime_market_data")
public class RealtimeMarketData {

    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 股票代码
     */
    private String tsCode;

    /**
     * 股票名称
     */
    private String name;

    /**
     * 当前价格
     */
    private BigDecimal price;

    /**
     * 涨跌额
     */
    private BigDecimal change;

    /**
     * 涨跌幅
     */
    private BigDecimal pctChange;

    /**
     * 成交量（手）
     */
    private Long volume;

    /**
     * 成交额（万元）
     */
    private BigDecimal amount;

    /**
     * 开盘价
     */
    private BigDecimal open;

    /**
     * 昨日收盘价
     */
    private BigDecimal preClose;

    /**
     * 最高价
     */
    private BigDecimal high;

    /**
     * 最低价
     */
    private BigDecimal low;

    /**
     * 时间戳
     */
    private LocalDateTime timestamp;
}