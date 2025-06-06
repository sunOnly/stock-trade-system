package com.trade.security.handler;

import com.alibaba.fastjson.JSON;
import com.trade.common.enums.ResultCodeEnum;
import com.trade.common.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义访问拒绝处理器
 * <p>
 * 当已认证的用户尝试访问其没有权限的资源时，此处理器被调用。
 * 它负责返回一个表示访问被拒绝的响应。
 * </p>
 *
 * @author Trade Team
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    /**
     * 处理访问被拒绝的情况。
     *
     * @param request               HTTP请求
     * @param response              HTTP响应
     * @param accessDeniedException 访问被拒绝异常
     * @throws IOException      IO异常
     * @throws ServletException Servlet异常
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        LOGGER.warn("Access denied for user '{}' to '{}': {}",
                request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : "anonymous",
                request.getRequestURI(),
                accessDeniedException.getMessage());

        Result<Void> result = Result.failure(ResultCodeEnum.FORBIDDEN, "您没有权限访问该资源");

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write(JSON.toJSONString(result));
        response.getWriter().flush();
    }
}