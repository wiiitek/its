package pl.kubiczak.test.spring.integration.demo.server.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SpringDocConfig {

    @Bean
    fun api(): OpenAPI =
        OpenAPI()
            .components(Components())
            .info(
                Info().title("Integration Tests Samples")
            )
}
