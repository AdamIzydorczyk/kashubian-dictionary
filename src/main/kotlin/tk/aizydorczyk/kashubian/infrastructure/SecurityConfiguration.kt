package tk.aizydorczyk.kashubian.infrastructure

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.channel.ChannelProcessingFilter
import org.springframework.stereotype.Component
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.OncePerRequestFilter
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.FILE_PATH
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.KASHUBIAN_ENTRY_PATH
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Configuration
@EnableWebSecurity
class SecurityConfiguration(private val corsFilter: CorsFilter) {

    @Bean
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        return httpSecurity.also {
            it.addFilterBefore(corsFilter, ChannelProcessingFilter::class.java)
                .antMatcher("/**")
                .csrf().disable()
                .cors()
                .configurationSource(corsConfig())
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/",
                        "/swagger-ui/**",
                        "/swagger-resources/**",
                        "/v2/**",
                        "/graphiql/**",
                        "/favicon.ico",
                        "/graphql",
                        "/custom-query/**",
                        "/$KASHUBIAN_ENTRY_PATH/{$FILE_PATH}")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/graphql")
                .permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .disable()
                .httpBasic()
        }.build()
    }

    private fun corsConfig(): CorsConfigurationSource {
        return UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**",
                    CorsConfiguration().apply {
                        allowedOrigins = listOf("*")
                        allowedMethods = listOf("*")
                        allowedHeaders = listOf("*")
                        allowCredentials = true
                    }
            )
        }
    }

    @Bean
    fun userDetailsService(): UserDetailsService = User.builder()
        .username("admin")
        .password("{noop}admin")
        .roles("ADMIN")
        .build().let { InMemoryUserDetailsManager(it) }
}

@Component
class CorsFilter : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain) {
        response.apply {
            setHeader("Access-Control-Allow-Origin", "*")
            setHeader("Access-Control-Allow-Methods", "*")
            setHeader("Access-Control-Allow-Headers", "*")
        }

        if ("OPTIONS" == request.method) {
            response.status = HttpServletResponse.SC_OK
        } else {
            filterChain.doFilter(request, response)
        }
    }

}

