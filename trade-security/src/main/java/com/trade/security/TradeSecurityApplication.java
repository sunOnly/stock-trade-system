package com.trade.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 安全模块启动类
 */
@EnableDiscoveryClient
@SpringBootApplication
public class TradeSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(TradeSecurityApplication.class, args);
    }

}