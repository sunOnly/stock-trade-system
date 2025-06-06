package com.trade.security.handler;

import com.alibaba.fastjson.JSON;
import com.trade.common.enums.ResultCodeEnum;
import com.trade.common.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义认证失败处理器
 * <p>
 * 当用户登录失败时，此处理器负责返回统一格式的错误响应。
 * </p>
 *
 * @author Trade Team
 */
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);

    /**
     * 处理认证失败。
     *
     * @param request   HTTP请求
     * @param response  HTTP响应
     * @param exception 认证异常
     * @throws IOException      IO异常
     * @throws ServletException Servlet异常
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        LOGGER.warn("Authentication failed: {}", exception.getMessage());

        Result<Void> result = Result.failure(ResultCodeEnum.LOGIN_FAILURE, exception.getMessage());

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 可以根据具体异常类型设置不同的状态码
        response.getWriter().write(JSON.toJSONString(result));
        response.getWriter().flush();
    }
}