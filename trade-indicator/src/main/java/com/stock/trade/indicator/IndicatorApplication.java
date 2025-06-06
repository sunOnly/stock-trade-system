package com.stock.trade.indicator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 技术指标服务启动类
 *
 * @author AI Assistant
 */
@SpringBootApplication(scanBasePackages = {"com.stock.trade"})
@EnableScheduling // 启用定时任务
public class IndicatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(IndicatorApplication.class, args);
    }

}