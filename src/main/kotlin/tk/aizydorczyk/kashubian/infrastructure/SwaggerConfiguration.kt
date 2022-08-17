package tk.aizydorczyk.kashubian.infrastructure

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.BasicAuth
import springfox.documentation.service.SecurityReference
import springfox.documentation.spi.DocumentationType.SWAGGER_2
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfiguration : WebMvcConfigurer {
    @Bean
    fun api(): Docket? {
        return Docket(SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("tk.aizydorczyk.kashubian")
                .or(RequestHandlerSelectors.basePackage("com.introproventures")))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo())
            .securitySchemes(listOf(BasicAuth("basicAuth")))
            .securityContexts(listOf(SecurityContext.builder()
                .securityReferences(listOf(SecurityReference("basicAuth", emptyArray())))
                .operationSelector { PathSelectors.ant("/**").test(it.requestMappingPattern()) }
                .build()))
    }

    private fun apiInfo(): ApiInfo = ApiInfoBuilder()
        .title("Kashubian Dictionary API")
        .description("[Link to GraphiQL](/graphiql)")
        .build()

    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addRedirectViewController("/", "/swagger-ui/")
    }
}