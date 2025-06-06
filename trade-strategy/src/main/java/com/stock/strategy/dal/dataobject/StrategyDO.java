package com.stock.strategy.dal.dataobject;

import com.stock.common.dal.dataobject.BaseDO; // 引入公共BaseDO

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 策略DO
 *
 * @author TraeAI
 */
@TableName("trade_strategy") // TODO: 确认表名是否正确
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StrategyDO extends BaseDO {

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 策略名称
     */
    private String strategyName;

    /**
     * 策略类型
     */
    private String strategyType;

    /**
     * 策略描述
     */
    private String description;

    /**
     * 策略内容 (例如: JSON格式的参数，或者脚本语言)
     */
    private String content;

    // TODO: 根据实际需求添加更多字段

}