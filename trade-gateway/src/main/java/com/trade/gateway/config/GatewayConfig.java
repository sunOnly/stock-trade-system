package com.trade.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * 网关配置类
 *
 * @author Trade Team
 */
@Configuration
public class GatewayConfig {

    /**
     * 配置CORS跨域支持
     *
     * @return CorsWebFilter
     */
    @Bean
    public CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedMethod("*"); // 允许所有请求方法 (GET, POST, PUT, DELETE, OPTIONS等)
        config.addAllowedOriginPattern("*"); // 允许所有来源，生产环境建议配置具体域名
        config.addAllowedHeader("*"); // 允许所有请求头
        config.setAllowCredentials(true); // 允许发送Cookie
        config.setMaxAge(3600L); // 预检请求的有效期，单位秒

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config); // 对所有路径生效

        return new CorsWebFilter(source);
    }
}