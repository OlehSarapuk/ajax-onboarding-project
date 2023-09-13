package com.example.ajaxonboardingproject.repository

import com.example.ajaxonboardingproject.model.ShoppingCart
import com.example.ajaxonboardingproject.model.User
import reactor.core.publisher.Mono

interface UserRepository {
    fun findByEmail(email: String): Mono<User>

    fun findShoppingCartByUserId(id: String): Mono<ShoppingCart>

    fun findById(id: String): Mono<User>

    fun save(user: User): Mono<User>
}
