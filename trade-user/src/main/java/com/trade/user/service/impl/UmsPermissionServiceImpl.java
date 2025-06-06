package com.trade.user.service.impl;

import com.trade.user.model.UmsPermission;
import com.trade.user.mapper.UmsPermissionMapper;
import com.trade.user.service.UmsPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台用户权限表 服务实现类
 * </p>
 *
 * @author Trade Team
 * @since 2024-03-20
 */
@Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

public class UmsPermissionServiceImpl extends ServiceImpl<UmsPermissionMapper, UmsPermission> implements UmsPermissionService {

    @Override
    public List<UmsPermission> treeList() {
        List<UmsPermission> permissionList = list();
        List<UmsPermission> result = permissionList.stream()
                .filter(permission -> permission.getParentId().equals(0L))
                .map(permission -> covert(permission, permissionList)).collect(Collectors.toList());
        return result;
    }

    /**
     * 将权限转换为带有子级的权限对象
     * 当找不到子级权限的时候map操作不会再递归调用covert
     */
    private UmsPermission covert(UmsPermission permission, List<UmsPermission> permissionList) {
        permission.setChildren(permissionList.stream()
                .filter(item -> item.getParentId().equals(permission.getId()))
                .map(item -> covert(item, permissionList))
                .collect(Collectors.toList()));
        return permission;
    }
}