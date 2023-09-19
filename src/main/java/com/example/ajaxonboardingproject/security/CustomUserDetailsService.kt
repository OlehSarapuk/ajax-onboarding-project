package com.example.ajaxonboardingproject.security

import com.example.ajaxonboardingproject.service.UserService
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.User.withUsername
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class CustomUserDetailsService(private val userService: UserService) : ReactiveUserDetailsService {

    @Suppress("SpreadOperator")
    override fun findByUsername(username: String): Mono<UserDetails> {
        return userService.findByEmail(username)
            .switchIfEmpty(Mono.error(NoSuchElementException("Can't get user with email: $username")))
            .map {
                withUsername(username).apply {
                    password(it.password)
                    roles(*it.roles.map { role ->  role.name }.toTypedArray()) }
                    .build()
            }
    }
}
