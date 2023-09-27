package com.example.ajaxonboardingproject.config

import com.example.ajaxonboardingproject.security.jwt.JwtSecurityContextRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class SecurityConfig(
    val userDetailsService: ReactiveUserDetailsService,
    val passwordEncoder: PasswordEncoder,
    val jwtSecurityContextRepository: JwtSecurityContextRepository
) {
    @Bean
    fun authenticationManager(): ReactiveAuthenticationManager {
        return UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService).apply {
            setPasswordEncoder(passwordEncoder)
        }
    }

    @Bean
    internal fun springSecurityFilterChain(
        http: ServerHttpSecurity
    ): SecurityWebFilterChain {
        return http
            .securityContextRepository(jwtSecurityContextRepository)
            .authorizeExchange {
                it.pathMatchers(
                    HttpMethod.POST,
                    "/register",
                    "/login"
                ).permitAll()
                it.pathMatchers(
                    HttpMethod.GET,
                    "/cinema-halls",
                    "/movies",
                    "/movie-sessions/available"
                ).hasRole("USER")
                it.pathMatchers(
                    HttpMethod.POST,
                    "/cinema-halls",
                    "/movies",
                    "/movie-sessions"
                ).hasRole("USER")
                it.pathMatchers(
                    HttpMethod.GET,
                    "/orders",
                    "/shopping-carts/by-user",
                    "/users/by-email"
                ).hasRole("USER")
                it.pathMatchers(HttpMethod.PUT, "/movie-sessions/{id}").hasRole("USER")
                it.pathMatchers(HttpMethod.DELETE, "/movie-sessions/{id}").hasRole("USER")
                it.pathMatchers(HttpMethod.POST, "/orders/complete").hasRole("USER")
                it.pathMatchers(HttpMethod.PUT, "/shopping-carts/movie-sessions").hasRole("USER")
            }
            .formLogin { it.disable() }
            .logout { it.disable() }
            .csrf { it.disable() }
            .httpBasic { it.disable() }
            .build()
    }
}
