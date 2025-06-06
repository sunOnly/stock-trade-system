package com.trade.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 网关应用启动类
 *
 * @author Trade Team
 */
@SpringBootApplication
@EnableDiscoveryClient // 开启服务注册与发现功能
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  Trade Gateway 启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                " .-------.       ____     __        \n" +
                " |  _ _   |      \\   \\   /  /    \n" +
                " | ( ' )  |       \\  _. /  '       \n" +
                " |(_ o _) /        _( )_ .'         \n" +
                " | (_,_).' __  ___(_ o _)'          \n" +
                " |  |\ \  |  ||   |(_,_)'         \n" +
                " |  | \ `'   /|   `-'  /           \n" +
                " |  |  \    /  \      /           \n" +
                " ''-'   `'-'    `-..-'              ");
    }
}