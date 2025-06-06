package com.trade.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 用户服务启动类
 * @author Trade Team
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.trade")
public class TradeUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(TradeUserApplication.class, args);
    }
}