package com.github.vitorgarcia03.dadocoletado.config;

import org.springframework.context.annotation.*;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {

        return new OpenAPI().info(new Info().title("API de Dados Coletados por Sonda Espacial")
                .version("1.0.0").description("Checkpoint 2 — Modelo 2 — Vitor Garcia Correia — RM 558516"));
    }
}
