package com.pawmart.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI pawMartOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("PawMart Ecommerce API")
                        .description("REST API for PawMart Ecommerce Backend")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Nguyen Dung")
                                .email("congdung1409@gmail.com"))
                        .license(new License()
                                .name("MIT License")))
                .externalDocs(new ExternalDocumentation()
                        .description("PawMart GitHub"));
    }
}
