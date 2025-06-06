package com.trade.user.mapper;

import com.trade.user.model.UmsAdmin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.trade.user.model.UmsPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 后台用户表 Mapper 接口
 * </p>
 *
 * @author Trade Team
 * @since 2024-03-20
 */
public interface UmsAdminMapper extends BaseMapper<UmsAdmin> {

    /**
     * 获取用户所有权限(包括角色权限和+-权限)
     */
    List<UmsPermission> getPermissionList(@Param("adminId") Long adminId);
}