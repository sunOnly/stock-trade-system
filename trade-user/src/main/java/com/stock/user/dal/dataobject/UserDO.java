package com.stock.user.dal.dataobject;

import com.stock.common.dal.dataobject.BaseDO; // 引入公共BaseDO

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 用户DO
 *
 * @author TraeAI
 */
@TableName("trade_user") // TODO: 确认表名是否正确, 通常用户表名为 sys_user 或 user
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserDO extends BaseDO {

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password; // TODO: 密码字段需要加密存储

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户状态（例如：启用，禁用）
     */
    private Integer status;

    // TODO: 根据实际需求添加更多字段

}