package com.trade.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 用户服务启动类
 *
 * @author Trade Team
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.trade.user.mapper") // 扫描Mapper接口
@ComponentScan(basePackages = {"com.trade.user", "com.trade.common.config"}) // 扫描通用配置，例如全局异常处理
public class UserApplication {

    /**
     * 主函数，启动用户服务应用。
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  Trade User Service 启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                " .-------.       ____     __        \n" +
                " |  _ _   |      \\\\   \\\\   /  /    \n" +
                " | ( ' )  |       \\\\  _. /  '       \n" +
                " |(_ o _) /        _( )_ .'         \n" +
                " | (_,_).' __  ___(_ o _)'          \n" +
                " |  |\\\\\\\\ \\  |  ||   |(_,_)'         \n" +
                " |  | \\\\ `'   /|   `-'  /           \n" +
                " |  |  \\\\    /  \\\\      /           \n" +
                " ''-'   `'-'    `-..-'              ");
    }
}