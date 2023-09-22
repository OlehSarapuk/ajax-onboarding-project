package com.example.ajaxonboardingproject.user.service

import com.example.ajaxonboardingproject.user.model.User
import reactor.core.publisher.Mono

interface UserService {
    fun add(user: User): Mono<User>

    fun get(id: String): Mono<User>

    fun findByEmail(email: String): Mono<User>
}
