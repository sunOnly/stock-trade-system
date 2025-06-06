package com.stock.trading.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stock.trading.user.dto.UmsAdminParam;
import com.stock.trading.user.model.UmsAdmin;

/**
 * 后台用户管理Service
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
}