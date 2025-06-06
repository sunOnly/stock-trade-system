package com.trade.strategy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 交易策略服务启动类
 */
@EnableDiscoveryClient
@SpringBootApplication
public class TradeStrategyApplication {

    public static void main(String[] args) {
        SpringApplication.run(TradeStrategyApplication.class, args);
    }

}