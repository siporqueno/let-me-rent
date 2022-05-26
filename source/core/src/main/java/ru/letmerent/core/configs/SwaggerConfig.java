package ru.letmerent.core.configs;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SwaggerConfig.
 *
 */
@Configuration
@AllArgsConstructor
public class SwaggerConfig {
    private SwaggerProperties swaggerProperties;

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title(swaggerProperties.getTitle())
                        .description(swaggerProperties.getDescription())
                        .version("0.1.0"))
                .externalDocs(new ExternalDocumentation()
                        .description(swaggerProperties.getContact().getName())
                        .url(swaggerProperties.getContact().getUrl()));
    }
}
