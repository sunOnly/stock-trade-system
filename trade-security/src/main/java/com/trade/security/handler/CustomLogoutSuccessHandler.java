package com.trade.security.handler;

import com.alibaba.fastjson.JSON;
import com.trade.common.enums.ResultCodeEnum;
import com.trade.common.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义登出成功处理器
 * <p>
 * 当用户成功登出后，此处理器负责返回统一格式的成功响应。
 * </p>
 *
 * @author Trade Team
 */
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomLogoutSuccessHandler.class);

    /**
     * 处理登出成功。
     *
     * @param request        HTTP请求
     * @param response       HTTP响应
     * @param authentication 认证信息（可能为null，如果会话已失效）
     * @throws IOException      IO异常
     * @throws ServletException Servlet异常
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication != null && authentication.getDetails() != null) {
            try {
                request.getSession().invalidate();
                LOGGER.info("User '{}' logged out successfully.", authentication.getName());
            } catch (Exception e) {
                LOGGER.error("Error invalidating session during logout for user '{}': {}", authentication.getName(), e.getMessage());
            }
        }

        Result<Void> result = Result.success(ResultCodeEnum.LOGOUT_SUCCESS);

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(result));
        response.getWriter().flush();
    }
}