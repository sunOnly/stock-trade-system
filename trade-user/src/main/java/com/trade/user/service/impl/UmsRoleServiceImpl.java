package com.trade.user.service.impl;

import com.trade.user.model.UmsRole;
import com.trade.user.mapper.UmsRoleMapper;
import com.trade.user.service.UmsRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台用户角色表 服务实现类
 * </p>
 *
 * @author Trade Team
 * @since 2024-03-20
 */
@Service
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.trade.user.mapper.UmsRolePermissionRelationMapper;
import com.trade.user.model.UmsPermission;
import com.trade.user.model.UmsRolePermissionRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class UmsRoleServiceImpl extends ServiceImpl<UmsRoleMapper, UmsRole> implements UmsRoleService {
    @Autowired
    private UmsRolePermissionRelationMapper rolePermissionRelationMapper;

    @Override
    public List<UmsPermission> getPermissionList(Long roleId) {
        return rolePermissionRelationMapper.getPermissionList(roleId);
    }

    @Override
    public int updatePermission(Long roleId, List<Long> permissionIds) {
        //删除之前绑定的关系
        LambdaQueryWrapper<UmsRolePermissionRelation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UmsRolePermissionRelation::getRoleId, roleId);
        rolePermissionRelationMapper.delete(wrapper);
        //批量插入新的关系
        List<UmsRolePermissionRelation> relationList = new ArrayList<>();
        for (Long permissionId : permissionIds) {
            UmsRolePermissionRelation relation = new UmsRolePermissionRelation();
            relation.setRoleId(roleId);
            relation.setPermissionId(permissionId);
            relationList.add(relation);
        }
        if (!CollectionUtils.isEmpty(relationList)) {
            for (UmsRolePermissionRelation relation : relationList) {
                rolePermissionRelationMapper.insert(relation);
            }
        }
        return permissionIds.size();
    }
}