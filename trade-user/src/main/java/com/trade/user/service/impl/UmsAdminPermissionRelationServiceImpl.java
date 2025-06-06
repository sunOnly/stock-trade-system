package com.trade.user.service.impl;

import com.trade.user.model.UmsAdminPermissionRelation;
import com.trade.user.mapper.UmsAdminPermissionRelationMapper;
import com.trade.user.service.UmsAdminPermissionRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台用户和权限关系表(除角色外的额外权限) 服务实现类
 * </p>
 *
 * @author Trade Team
 * @since 2024-03-20
 */
@Service
public class UmsAdminPermissionRelationServiceImpl extends ServiceImpl<UmsAdminPermissionRelationMapper, UmsAdminPermissionRelation> implements UmsAdminPermissionRelationService {

}