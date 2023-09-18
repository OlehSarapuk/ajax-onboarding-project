package com.example.ajaxonboardingproject.security

import com.example.ajaxonboardingproject.model.User
import com.example.ajaxonboardingproject.service.UserService
import org.springframework.security.core.userdetails.User.withUsername
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class CustomUserDetailsService(private val userService: UserService) : UserDetailsService {
    @Suppress("SpreadOperator")
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userService.findByEmail(username)
            .switchIfEmpty(Mono.error(NoSuchElementException("Can't get user with email: $username")))
            .block()!!
        return withUsername(username).apply {
            password(user.password)
            roles(*user.roles.map { it.name }.toTypedArray())
        }.build()
    }
}
