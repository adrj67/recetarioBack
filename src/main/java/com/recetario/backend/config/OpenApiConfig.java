package com.recetario.backend.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API de Recetas")
                .version("1.0")
                .description("Documentación de la API para la aplicación de recetas. Aquí podrás consultar los endpoints disponibles para recetas, ingredientes y más.")
                .contact(new Contact()
                    .name("Adrian Rojo")
                    .email("adrj67@gmail.com")
                    .url("https://github.com/adrj67?tab=repositories"))
                        );
    }
}
