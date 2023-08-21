package com.example.ajaxonboardingproject.config

import com.example.ajaxonboardingproject.security.jwt.JwtTokenFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
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
                .authorizeHttpRequests{ auth ->
                    auth.anyRequest().permitAll()
                }
                .authenticationProvider(authenticationProvider())
                .csrf{it.disable()}
                .httpBasic{it.disable()}
                .headers{ it.frameOptions().disable()}
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
                .build()
    }
}