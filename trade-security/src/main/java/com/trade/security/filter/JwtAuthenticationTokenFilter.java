package com.trade.security.filter;

import com.trade.common.constant.CommonConstant;
import com.trade.common.util.JwtUtils;
import com.trade.security.service.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT认证令牌过滤器
 * <p>
 * 该过滤器在每个请求中检查JWT令牌的有效性，如果令牌有效，则将认证信息设置到Spring Security上下文中。
 * </p>
 *
 * @author Trade Team
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /**
     * 执行过滤器逻辑。
     *
     * @param request     HTTP请求
     * @param response    HTTP响应
     * @param filterChain 过滤器链
     * @throws ServletException Servlet异常
     * @throws IOException      IO异常
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(CommonConstant.TOKEN_HEADER);
        if (StringUtils.hasText(authHeader) && authHeader.startsWith(CommonConstant.TOKEN_PREFIX)) {
            String authToken = authHeader.substring(CommonConstant.TOKEN_PREFIX.length());
            try {
                if (jwtUtils.validateToken(authToken)) {
                    String username = jwtUtils.getUsernameFromToken(authToken);
                    LOGGER.info("Authenticated user: {}, setting security context", username);

                    // 当token存在并且有效时，设置Spring Security上下文
                    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                        if (jwtUtils.validateToken(authToken, userDetails)) { // 再次校验token是否与userDetails匹配
                            UsernamePasswordAuthenticationToken authentication =
                                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                            LOGGER.debug("User '{}' set in security context", username);
                        } else {
                            LOGGER.warn("Token validation failed for user '{}' against UserDetails.", username);
                        }
                    } else if (username == null) {
                        LOGGER.warn("Username from token is null.");
                    }
                } else {
                     LOGGER.warn("Invalid JWT token: {}", authToken);
                }
            } catch (Exception e) {
                LOGGER.error("Error processing JWT token: {}", e.getMessage());
                // 可以选择清除SecurityContext，以防部分认证信息残留
                // SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(request, response);
    }
}