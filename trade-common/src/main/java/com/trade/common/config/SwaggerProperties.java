package com.trade.common.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Swagger属性配置
 * @author Trade Team
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {
    /**
     * API文档生成基础路径
     */
    private String apiBasePackage;
    /**
     * 是否开启Swagger
     */
    private boolean enable;
    /**
     * 页面标题
     */
    private String title;
    /**
     * 页面描述
     */
    private String description;
    /**
     * 版本号
     */
    private String version;
    /**
     * 许可证名称
     */
    private String license;
    /**
     * 许可证URL
     */
    private String licenseUrl;
    /**
     * 外部文档地址
     */
    private String docUrl;
    /**
     * 外部文档描述
     */
    private String docDescription;
    /**
     * 分组名称
     */
    private String group;
}