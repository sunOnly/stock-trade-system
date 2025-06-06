package com.trade.strategy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 交易策略定义实体类
 *
 * @author AI Assistant
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ts_strategy_definition")
public class StrategyDefinition implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 策略ID (主键, 自增)
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 策略名称 (必填, 唯一)
     */
    private String name;

    /**
     * 策略描述
     */
    private String description;

    /**
     * 策略作者
     */
    private String author;

    /**
     * 策略版本
     */
    private String version;

    /**
     * 策略类型 (例如: "TREND_FOLLOWING", "MEAN_REVERSION", "EVENT_DRIVEN")
     * 可以考虑使用枚举类定义
     */
    private String type;

    /**
     * 策略脚本内容或配置 (存储策略的具体逻辑或配置)
     * 对于复杂策略，可能存储脚本语言代码 (如Python, Groovy) 或JSON/XML配置
     */
    private String scriptContent; // 数据库中建议使用TEXT类型

    /**
     * 策略参数 (JSON格式，存储策略的可配置参数及其默认值)
     * 例如: {"MA_short": 5, "MA_long": 20, "stop_loss_pct": 0.05}
     */
    private String parameters; // 数据库中建议使用TEXT/JSON类型

    /**
     * 策略状态 (例如: "DRAFT", "ACTIVE", "INACTIVE", "ARCHIVED")
     * 可以考虑使用枚举类定义
     */
    private String status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}