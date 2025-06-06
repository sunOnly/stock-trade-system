package com.trade.user.service;

import com.trade.user.model.UmsRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 后台用户角色表 服务类
 * </p>
 *
 * @author Trade Team
 * @since 2024-03-20
 */
import com.trade.user.model.UmsPermission;

import java.util.List;

public interface UmsRoleService extends IService<UmsRole> {
    /**
     * 获取角色相关权限
     */
    List<UmsPermission> getPermissionList(Long roleId);

    /**
     * 修改角色权限
     */
    int updatePermission(Long roleId, List<Long> permissionIds);
}