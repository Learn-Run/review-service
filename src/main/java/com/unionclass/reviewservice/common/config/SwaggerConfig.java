package com.unionclass.reviewservice.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        tags = {
                @Tag(name = "review", description = "리뷰 관련 API 입니다."),
                @Tag(name = "member-review", description = "회원에 대한 리뷰 집계 관련 API 입니다.")
        }
)
@Configuration
public class SwaggerConfig {

    private static final String BEARER_TOKEN_PREFIX = "Bearer";

    @Bean
    public OpenAPI openAPI() {
        String securityJwtName = "JWT";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(securityJwtName);
        Components components = new Components()
                .addSecuritySchemes(securityJwtName, new SecurityScheme()
                        .name(securityJwtName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme(BEARER_TOKEN_PREFIX)
                        .bearerFormat(securityJwtName));

        return new OpenAPI()
                .addSecurityItem(securityRequirement)
                .components(components)
                .addServersItem(new Server().url("/review-service"))
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("REVIEW-SERVICE API DOCS")
                .description("review-service API 테스트를 위한 Swagger UI")
                .version("1.0.0");
    }
}