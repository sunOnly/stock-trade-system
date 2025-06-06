package com.trade.marketdata.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author mac
 * @date 2024/7/16
 * @description WebClient 配置类
 */
@Configuration
public class WebClientConfig {

    private final TushareConfig tushareConfig;

    public WebClientConfig(TushareConfig tushareConfig) {
        this.tushareConfig = tushareConfig;
    }

    /**
     * 配置 WebClient Bean，用于调用 Tushare API
     * @return WebClient 实例
     */
    @Bean
    public WebClient tushareWebClient() {
        return WebClient.builder()
                .baseUrl(tushareConfig.getApiUrl())
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}