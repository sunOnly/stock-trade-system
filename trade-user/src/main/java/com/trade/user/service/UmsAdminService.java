package com.trade.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.trade.common.domain.UserDto;
import com.trade.user.dto.UmsAdminParam;
import com.trade.user.dto.UmsAdminUpdatePasswordParam;
import com.trade.user.model.UmsAdmin;
import com.trade.user.model.UmsPermission;

import java.util.List;

/**
 * 后台管理员Service
 * Created by macro on 2018/4/26.
 */
public interface UmsAdminService extends IService<UmsAdmin> {
    /**
     * 根据用户名获取后台管理员
     */
    UmsAdmin getAdminByUsername(String username);

    /**
     * 注册功能
     */
    UmsAdmin register(UmsAdminParam umsAdminParam);

    /**
     * 登录功能
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username, String password);

    /**
     * 根据用户id获取用户所有权限（角色权限和+-权限）
     */
    List<UmsPermission> getPermissionList(Long adminId);

    /**
     * 修改密码
     */
    int updatePassword(UmsAdminUpdatePasswordParam umsAdminUpdatePasswordParam);

    /**
     * 获取用户信息
     */
    UserDto loadUserByUsername(String username);

    /**
     * 获取用户对应角色
     */
    List<com.trade.user.model.UmsRole> getRoleList(Long adminId);

    /**
     * 刷新token的功能
     * @param oldToken 旧的token
     */
    String refreshToken(String oldToken);
}