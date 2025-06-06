package com.trade.marketdata.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author mac
 * @date 2024/7/16
 * @description 股票基本信息实体类
 */
@Data
@TableName("stock_basic")
public class StockBasic implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * TS股票代码
     */
    private String tsCode;

    /**
     * 股票代码
     */
    private String symbol;

    /**
     * 股票名称
     */
    private String name;

    /**
     * 股票简称
     */
    private String area;

    /**
     * 所属省份
     */
    private String province;

    /**
     * 所属城市
     */
    private String city;

    /**
     * 所属行业
     */
    private String industry;

    /**
     * 股票全称
     */
    private String fullname;

    /**
     * 英文全称
     */
    private String enname;

    /**
     * 市场类型 (主板/创业板/科创板等)
     */
    private String market;

    /**
     * 交易所代码
     */
    private String exchange;

    /**
     * 交易货币
     */
    private String currType;

    /**
     * 上市状态 L上市 D退市 P暂停上市
     */
    private String listStatus;

    /**
     * 上市日期
     */
    private LocalDate listDate;

    /**
     * 退市日期
     */
    private LocalDate delistDate;

    /**
     * 是否沪深港通标的，N否 H沪股通 S深股通
     */
    private String isHs;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}