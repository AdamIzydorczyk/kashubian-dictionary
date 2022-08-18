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


@Configuration
@EnableWebSecurity
class SecurityConfiguration {

    @Bean
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        return httpSecurity.also {
            it.antMatcher("/**")
                .csrf().disable()
                .cors().disable()
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
                        "/kashubian-entry/{entryId}/file")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/graphql")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .disable()
                .httpBasic()
        }.build()
    }

    @Bean
    fun userDetailsService(): UserDetailsService = User.builder()
        .username("admin")
        .password("{noop}admin")
        .roles("ADMIN")
        .build().let { InMemoryUserDetailsManager(it) }
}