package com.stock.marketdata.dal.dataobject;

import com.stock.common.dal.dataobject.BaseDO; // 引入公共BaseDO

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 行情数据DO
 *
 * @author TraeAI
 */
@TableName("market_data") // TODO: 确认表名是否正确
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MarketDataDO extends BaseDO {

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 股票代码
     */
    private String stockCode;

    /**
     * 股票名称
     */
    private String stockName;

    /**
     * 最新价格
     */
    private Double latestPrice;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    // TODO: 根据实际需求添加更多字段

}