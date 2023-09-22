package com.example.ajaxonboardingproject.authentication.service

import com.example.ajaxonboardingproject.user.model.User
import reactor.core.publisher.Mono

interface AuthenticationService {
    fun register(email: String, password: String): Mono<User>

    fun login(login: String, password: String): Mono<User>
}
