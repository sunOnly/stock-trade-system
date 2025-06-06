package com.trade.backtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 交易回测服务启动类
 */
@EnableDiscoveryClient
@SpringBootApplication
public class TradeBacktestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TradeBacktestApplication.class, args);
    }

}