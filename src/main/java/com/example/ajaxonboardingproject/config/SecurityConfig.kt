package com.example.ajaxonboardingproject.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain


@Configuration
@EnableWebFluxSecurity
class SecurityConfig(
    val userDetailsService: UserDetailsService,
//    val jwtTokenFilter: JwtTokenFilter,
    val passwordEncoder: PasswordEncoder
) {
    @Bean
    fun authenticationProvider(): ReactiveAuthenticationManager {
        val authenticationProvider = DaoAuthenticationProvider()
        authenticationProvider.setUserDetailsService(userDetailsService)
        authenticationProvider.setPasswordEncoder(passwordEncoder)
        return authenticationProvider
    }

//    @Bean
//    fun springSecurityFilterChain(
//        http: ServerHttpSecurity
//    ): SecurityWebFilterChain {
//        http
//            .csrf { it.disable() }
//            .authorizeExchange{
//                it.anyExchange().permitAll()
//            }
//            .httpBasic{}
//        return http.build()
//    }

    @Bean
    internal fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
//            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeExchange{
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
                ).authenticated()
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
            .formLogin{ it.disable() }
            .logout{ it.disable() }
//            .authenticationProvider(authenticationProvider())
            .authenticationManager(authenticationProvider())
            .csrf { it.disable() }
            .httpBasic { it.disable() }
            .headers { header -> header.frameOptions{it.disable()} }
            .addFilterBefore()
//            .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }
}
