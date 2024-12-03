package com.golden.raspberry.awards.configs.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Golden Raspberry Awards API")
                        .description("API for managing and retrieving information about Golden Raspberry Awards, including producers with the smallest and largest award intervals.")
                        .version("1.0")
                        .termsOfService("https://example.com/terms")
                        .contact(new Contact()
                                .name("Golden Raspberry Support")
                                .url("https://example.com/support")
                                .email("support@example.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html"))
                );
    }

}
