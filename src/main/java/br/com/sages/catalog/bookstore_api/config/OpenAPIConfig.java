package br.com.sages.catalog.bookstore_api.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI OpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Bookstore API")
                        .description("A RESTFul API for an independent bookstore which allows customers to browse available books by genre and author.")
                        .version("0.0.1-SNAPSHOT")
                );
    }
}
