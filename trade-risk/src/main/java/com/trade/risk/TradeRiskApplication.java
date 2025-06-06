package com.trade.risk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 风险服务启动类
 * Created by macro on 2020/8/3.
 */
@EnableDiscoveryClient
@SpringBootApplication
public class TradeRiskApplication {
    public static void main(String[] args) {
        SpringApplication.run(TradeRiskApplication.class, args);
    }
}