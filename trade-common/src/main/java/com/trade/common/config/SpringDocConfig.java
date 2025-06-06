package com.trade.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>SpringDoc OpenAPI 配置类</p>
 * <p>用于生成API接口文档</p>
 *
 * @author creator
 * @since 2024-01-01
 */
@Configuration
public class SpringDocConfig {

    @Value("${spring.application.name:default-service}")
    private String applicationName;

    /**
     * 配置 OpenAPI 基本信息
     *
     * @return OpenAPI 实例
     */
    @Bean
    public OpenAPI customOpenAPI(
            @Value("${springdoc.version:1.0.0}") String appVersion,
            @Value("${springdoc.title:API Documentation}") String title,
            @Value("${springdoc.description:API documentation for the service}") String description,
            @Value("${springdoc.contact.name:API Support}") String contactName,
            @Value("${springdoc.contact.email:support@example.com}") String contactEmail,
            @Value("${springdoc.contact.url:https://example.com}") String contactUrl,
            @Value("${springdoc.license.name:Apache 2.0}") String licenseName,
            @Value("${springdoc.license.url:https://www.apache.org/licenses/LICENSE-2.0.html}") String licenseUrl) {

        // 优化：将 applicationName 用于 title 和 description (如果它们是默认值)
        String effectiveTitle = title.equals("API Documentation") ? applicationName + " API Documentation" : title;
        String effectiveDescription = description.equals("API documentation for the service") ?
                "API documentation for the " + applicationName + " service." : description;

        return new OpenAPI()
                .info(new Info()
                        .title(effectiveTitle)
                        .version(appVersion)
                        .description(effectiveDescription)
                        .contact(new Contact()
                                .name(contactName)
                                .email(contactEmail)
                                .url(contactUrl))
                        .license(new License()
                                .name(licenseName)
                                .url(licenseUrl)));
    }

    /**
     * 创建一个默认的 API 分组
     * <p>
     * 可以根据需要创建多个 GroupedOpenApi Bean 来对 API 进行分组展示。
     * 例如，可以按模块、按版本或按访问权限进行分组。
     * </p>
     *
     * @return GroupedOpenApi 实例
     */
    @Bean
    public GroupedOpenApi defaultApiGroup() {
        // 优化：使用 applicationName 作为 group 名称，使其更具辨识度
        String groupName = applicationName.replace("-service", "") + "-apis";
        return GroupedOpenApi.builder()
                .group(groupName) // API 分组的名称
                .packagesToScan("com.trade."+ applicationName.replace("trade-","") + ".controller") // 指定扫描的包路径，需要根据模块名动态调整
                // .pathsToMatch("/api/**") // 可以通过路径匹配来包含特定的API
                // .displayName(applicationName + " APIs") // 分组的显示名称
                .build();
    }

    // 示例：为特定模块创建API分组 (如果需要)
    // @Bean
    // public GroupedOpenApi userApiGroup() {
    //     return GroupedOpenApi.builder()
    //             .group("user-management")
    //             .packagesToScan("com.trade.user.controller") // 假设用户模块的controller在此包下
    //             .pathsToMatch("/user/**")
    //             .displayName("User Management APIs")
    //             .build();
    // }

    // @Bean
    // public GroupedOpenApi productApiGroup() {
    //     return GroupedOpenApi.builder()
    //             .group("product-catalog")
    //             .packagesToScan("com.trade.product.controller") // 假设产品模块的controller在此包下
    //             .pathsToMatch("/product/**")
    //             .displayName("Product Catalog APIs")
    //             .build();
    // }
}