package galicia.challenge.dollarService.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Dollar Service API") // Título específico para el microservicio de Dólar
                        .version("1.0.0")
                        .description("API para obtener el precio del dólar oficial y blue.") // Descripción específica
                        .termsOfService("https://example.com/terms")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org")));
    }
}