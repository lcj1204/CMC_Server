package com.sctk.cmc.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        String authorizeTitle = "Authentication Token";

        Components components = new Components().addSecuritySchemes(
                authorizeTitle,
                new SecurityScheme()
                        .name(authorizeTitle)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("Bearer")
                        .bearerFormat("JWT")
        );

        return new OpenAPI()
                .components(components)
                .info(new Info().title("CMC Server API Document")
                        .description("CMC Server의 API Document입니다.")
                        .version("v1"));
    }
}
