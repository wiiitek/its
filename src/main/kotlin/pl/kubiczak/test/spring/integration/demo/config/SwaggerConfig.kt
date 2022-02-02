package pl.kubiczak.test.spring.integration.demo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
@EnableWebMvc
class SwaggerConfig {

    @Bean
    fun api(): Docket =
        Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(
                RequestHandlerSelectors.withClassAnnotation(RestController::class.java)
            )
            .paths(PathSelectors.any())
            .build()
}
