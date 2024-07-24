package ar.com.demo.reba.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info =  @Info(
                title = "Challenge Reba",
                version = "1.0.0",
                description = "Solución de challenge para Reba"
        )
)
public class OpenApiConfig {

}
