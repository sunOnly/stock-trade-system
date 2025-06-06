package com.trade.user.service;

import com.trade.user.model.UmsPermission;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 后台用户权限表 服务类
 * </p>
 *
 * @author Trade Team
 * @since 2024-03-20
 */
import java.util.List;

public interface UmsPermissionService extends IService<UmsPermission> {
    /**
     * 以层级结构返回所有权限
     */
    List<UmsPermission> treeList();
}