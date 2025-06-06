package com.trade.common.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;

/**
 * Swagger基础配置
 * @author Trade Team
 */
public abstract class BaseSwaggerConfig {

    /**
     * 自定义Swagger配置
     */
    public abstract SwaggerProperties swaggerProperties();

    @Bean
    public GroupedOpenApi publicApi() {
        SwaggerProperties swaggerProperties = swaggerProperties();
        return GroupedOpenApi.builder()
                .group(swaggerProperties.getGroup())
                .pathsToMatch(swaggerProperties.getApiBasePackage())
                .build();
    }

    @Bean
    public OpenAPI springShopOpenAPI() {
        SwaggerProperties swaggerProperties = swaggerProperties();
        return new OpenAPI()
                .info(new Info().title(swaggerProperties.getTitle())
                        .description(swaggerProperties.getDescription())
                        .version(swaggerProperties.getVersion())
                        .license(new License().name(swaggerProperties.getLicense()).url(swaggerProperties.getLicenseUrl())))
                .externalDocs(new ExternalDocumentation()
                        .description(swaggerProperties.getDocDescription())
                        .url(swaggerProperties.getDocUrl()))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("BearerAuth",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
    }
}