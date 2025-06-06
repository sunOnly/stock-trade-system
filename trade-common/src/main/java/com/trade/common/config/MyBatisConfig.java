package com.trade.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis配置类
 * @author Trade Team
 */
@Configuration
@MapperScan({"com.trade.common.mapper"})
public class MyBatisConfig {
}