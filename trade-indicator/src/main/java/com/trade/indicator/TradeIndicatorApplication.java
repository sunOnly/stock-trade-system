package com.trade.indicator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 交易指标服务启动类
 */
@EnableDiscoveryClient
@SpringBootApplication
public class TradeIndicatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(TradeIndicatorApplication.class, args);
    }

}