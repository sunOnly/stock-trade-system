package com.trade.strategy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 策略服务启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients // 如果需要调用其他服务，则开启 Feign
public class StrategyApplication {

    public static void main(String[] args) {
        SpringApplication.run(StrategyApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  策略服务启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                " .-------.       ____     __        \n" +
                " |  _ _   \\      \\   \\   /  /    \\n" +
                " | ( ' )  |       \  _. /  '       \n" +
                " |(_ o _) /        _( )_ .'         \n" +
                " | (_,_).' __  ___(_ o _)'          \n" +
                " |  |\ \  |  ||   |(_,_)'         \n" +
                " |  | \ `'   /|   `-'  /           \n" +
                " |  |  \    /  \      /           \n" +
                " ''-'   `'-'    `-..-'              ");
    }

}