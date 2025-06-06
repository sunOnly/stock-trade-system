package com.trade.user.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MyBatis Plus 配置类
 * <p>
 * 用于配置MyBatis Plus的相关特性，如分页插件、Mapper接口扫描等。
 * </p>
 *
 * @author Trade Team
 */
@Configuration
@EnableTransactionManagement // 开启声明式事务管理
@MapperScan("com.trade.user.mapper") // 扫描Mapper接口，UserApplication中已有，这里可省略或保留以明确
public class MyBatisPlusConfig {

    /**
     * 配置MyBatis Plus拦截器，用于支持分页等功能。
     * <p>
     * 新版MyBatis Plus（3.4.0+）推荐使用 {@link MybatisPlusInterceptor}。
     * 旧版的分页插件是 {@code PaginationInterceptor}。
     * </p>
     *
     * @return MybatisPlusInterceptor 实例
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 添加分页插件，并指定数据库类型为MySQL
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    // 如果需要其他MyBatis Plus的配置，例如乐观锁插件、SQL性能规范插件等，可以在这里添加
    // 例如：
    // /**
    //  * 乐观锁插件 (如果需要)
    //  */
    // @Bean
    // public OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor() {
    //     return new OptimisticLockerInnerInterceptor();
    // }

    // /**
    //  * 防止全表更新与删除插件 (如果需要)
    //  */
    // @Bean
    // public BlockAttackInnerInterceptor blockAttackInnerInterceptor() {
    //     return new BlockAttackInnerInterceptor();
    // }
}