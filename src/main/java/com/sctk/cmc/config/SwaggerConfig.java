package com.sctk.cmc.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        String authorizeTitle = "Authorization";

        SecurityScheme bearerAuth = new SecurityScheme()
                .name(authorizeTitle)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("Authorization")
                .in(SecurityScheme.In.HEADER)
                .name(HttpHeaders.AUTHORIZATION);

        // Security 요청 설정
        SecurityRequirement addSecurityItem = new SecurityRequirement();
        addSecurityItem.addList("Authorization");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes(authorizeTitle, bearerAuth))
                .addSecurityItem(addSecurityItem)
                .info(new Info().title("CMC Server API Document")
                        .description("CMC Server의 API Document입니다.")
                        .version("v1"));
    }
}
