package com.stock.common.dal.dataobject;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础 DO 类，所有模块的 DO 类都应继承此类
 *
 * @author TraeAI
 */
@Data
public abstract class BaseDO implements Serializable {

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 最后更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 创建者，例如：userId
     * TODO: 考虑从上下文中自动获取创建者
     */
    @TableField(fill = FieldFill.INSERT)
    private String creator;

    /**
     * 更新者，例如：userId
     * TODO: 考虑从上下文中自动获取更新者
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updater;

    /**
     * 是否删除
     */
    private Boolean deleted;

}