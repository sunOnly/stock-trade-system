package com.trade.security.handler;

import com.alibaba.fastjson.JSON;
import com.trade.common.enums.ResultCodeEnum;
import com.trade.common.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义认证入口点
 * <p>
 * 当匿名用户尝试访问受保护的资源而未提供有效的认证凭证时，此处理器被调用。
 * 它负责返回一个表示需要认证的响应。
 * </p>
 *
 * @author Trade Team
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);

    /**
     * 开始认证过程。
     *
     * @param request       HTTP请求
     * @param response      HTTP响应
     * @param authException 认证异常
     * @throws IOException      IO异常
     * @throws ServletException Servlet异常
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        LOGGER.warn("Unauthorized access attempt to '{}': {}", request.getRequestURI(), authException.getMessage());

        Result<Void> result = Result.failure(ResultCodeEnum.UNAUTHORIZED, "请求未授权，请先登录");

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(JSON.toJSONString(result));
        response.getWriter().flush();
    }
}