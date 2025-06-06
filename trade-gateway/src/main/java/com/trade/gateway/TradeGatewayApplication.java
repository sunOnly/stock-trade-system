package com.trade.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 网关服务启动类
 * Created by macro on 2020/6/17.
 */
@EnableDiscoveryClient
@SpringBootApplication
public class TradeGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(TradeGatewayApplication.class, args);
    }
}