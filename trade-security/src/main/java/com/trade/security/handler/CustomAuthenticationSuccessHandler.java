package com.trade.security.handler;

import com.alibaba.fastjson.JSON;
import com.trade.common.constant.CommonConstant;
import com.trade.common.enums.ResultCodeEnum;
import com.trade.common.util.JwtUtils;
import com.trade.common.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义认证成功处理器
 * <p>
 * 当用户成功登录后，此处理器负责生成JWT并将其返回给客户端。
 * </p>
 *
 * @author Trade Team
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 处理认证成功。
     *
     * @param request        HTTP请求
     * @param response       HTTP响应
     * @param authentication 认证信息
     * @throws IOException      IO异常
     * @throws ServletException Servlet异常
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        String token = jwtUtils.generateToken(userDetails);

        LOGGER.info("User '{}' authenticated successfully. Generating JWT token.", username);

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put(CommonConstant.TOKEN_HEADER_PREFIX_WITH_SPACE.trim(), token);

        Result<Map<String, String>> result = Result.success(ResultCodeEnum.LOGIN_SUCCESS, tokenMap);

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(result));
        response.getWriter().flush();
    }
}