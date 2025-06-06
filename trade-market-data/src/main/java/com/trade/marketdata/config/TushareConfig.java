package com.trade.marketdata.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author mac
 * @date 2024/7/16
 * @description Tushare API 配置类
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "tushare")
public class TushareConfig {

    /**
     * Tushare API 的基础 URL
     */
    private String apiUrl;

    /**
     * Tushare API 的 Token
     */
    private String token;

}