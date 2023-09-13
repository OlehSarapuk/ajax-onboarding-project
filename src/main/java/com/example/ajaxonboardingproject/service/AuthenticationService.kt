package com.example.ajaxonboardingproject.service

import com.example.ajaxonboardingproject.model.User
import reactor.core.publisher.Mono

interface AuthenticationService {
    fun register(email: String, password: String): Mono<User>

    fun login(login: String, password: String): Mono<User>
}
