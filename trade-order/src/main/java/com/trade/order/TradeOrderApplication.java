package com.trade.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 订单服务启动类
 * Created by macro on 2020/8/3.
 */
@EnableDiscoveryClient
@SpringBootApplication
public class TradeOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(TradeOrderApplication.class, args);
    }
}