package com.trade.common.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <p>MyBatis Plus 配置类</p>
 *
 * @author creator
 * @since 2024-01-01
 */
@Configuration
@EnableTransactionManagement // 开启事务管理
@MapperScan("com.trade.*.mapper") // 扫描 Mapper 接口，根据实际模块调整或在各模块单独配置
public class MyBatisPlusConfig {

    /**
     * 配置 MyBatis Plus 拦截器
     *
     * @return MybatisPlusInterceptor 实例
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 1. 添加分页插件
        // DbType 用于指定数据库类型，MySQL为例
        // optimizeJoin 是否优化left join连接查询，默认为false，建议保持false以保证结果正确性
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        // paginationInnerInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        // paginationInnerInterceptor.setMaxLimit(500L);
        interceptor.addInnerInterceptor(paginationInnerInterceptor);

        // 2. 添加乐观锁插件 (如果需要)
        // 当要更新一条记录的时候，希望这条记录没有被别人更新，也就是说实现线程安全的数据更新
        // 需要在实体类的字段上加上 @Version 注解
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());

        // 3. 防止全表更新与删除插件 (如果需要, 注意在生产环境谨慎使用，因为它会阻止没有 WHERE 条件的 UPDATE 和 DELETE 操作)
        // interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());

        return interceptor;
    }

    // 如果使用了 MyBatis Plus 的逻辑删除功能，可以在这里配置全局的逻辑删除字段等
    // 例如，在 application.yml/properties 中配置：
    // mybatis-plus.global-config.db-config.logic-delete-field=deleted # 全局逻辑删除的实体字段名(since 3.3.0,配置后可以忽略不配置步骤2)
    // mybatis-plus.global-config.db-config.logic-delete-value=1 # 逻辑已删除值(默认为 1)
    // mybatis-plus.global-config.db-config.logic-not-delete-value=0 # 逻辑未删除值(默认为 0)
}