package com.kruger.kevaluacion.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI kevaluacionOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Evaluación Técnica - Kruger")
                        .version("1.0")
                        .description("Documentación de endpoints para autenticación, usuarios, proyectos y tareas"))
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .in(SecurityScheme.In.HEADER)
                                        .name("Authorization")
                        ))
                .addSecurityItem(new SecurityRequirement().addList("bearer-key"));
    }
}
