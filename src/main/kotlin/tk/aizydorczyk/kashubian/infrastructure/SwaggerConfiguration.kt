package tk.aizydorczyk.kashubian.infrastructure

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class SwaggerConfiguration : WebMvcConfigurer {

    @Bean
    fun customOpenAPI(): OpenAPI = OpenAPI()
        .components(Components().addSecuritySchemes("basicAuth",
                SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))
        .info(Info()
            .title("Kashubian Dictionary API")
            .description("[Link to GraphiQL](/graphiql)"))


    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addRedirectViewController("/", "/swagger-ui.html")
    }
}