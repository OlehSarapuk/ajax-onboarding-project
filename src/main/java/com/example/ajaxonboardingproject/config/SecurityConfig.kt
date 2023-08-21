package com.example.ajaxonboardingproject.config

import com.example.ajaxonboardingproject.security.jwt.JwtTokenFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
        val userDetailsService: UserDetailsService,
        val jwtTokenFilter: JwtTokenFilter,
        val passwordEncoder: PasswordEncoder) {
    @Bean
    fun authenticationProvider() : AuthenticationProvider {
        val authenticationProvider : DaoAuthenticationProvider = DaoAuthenticationProvider()
        authenticationProvider.setUserDetailsService(userDetailsService)
        authenticationProvider.setPasswordEncoder(passwordEncoder)
        return authenticationProvider
    }

    @Bean
    protected fun filterChain(http : HttpSecurity) : SecurityFilterChain {
        return http
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests{
                (it.requestMatchers(HttpMethod.POST,
                        "/register",
                        "/login").permitAll())
                (it.requestMatchers(HttpMethod.GET,
                        "/cinema-halls",
                        "/movies",
                        "/movie-sessions/available").authenticated())
                (it.requestMatchers(HttpMethod.POST,
                        "/cinema-halls",
                        "/movies",
                        "/movie-sessions").authenticated())
                (it.requestMatchers(HttpMethod.GET,
                        "/orders",
                        "/shopping-carts/by-user",
                        "/users/by-email").authenticated())
                (it.requestMatchers(HttpMethod.PUT, "/movie-sessions/{id}").authenticated())
                (it.requestMatchers(HttpMethod.DELETE, "/movie-sessions/{id}").authenticated())
                (it.requestMatchers(HttpMethod.POST, "/orders/complete").authenticated())
                (it.requestMatchers(HttpMethod.PUT, "/shopping-carts/movie-sessions").authenticated())
            }
            .authenticationProvider(authenticationProvider())
            .csrf{it.disable()}
            .httpBasic{it.disable()}
            .headers{ it.frameOptions().disable()}
            .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }
}