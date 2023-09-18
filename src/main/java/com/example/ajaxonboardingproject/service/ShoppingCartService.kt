package com.example.ajaxonboardingproject.service

import com.example.ajaxonboardingproject.model.MovieSession
import com.example.ajaxonboardingproject.model.ShoppingCart
import reactor.core.publisher.Mono

interface ShoppingCartService {
    fun addSession(movieSession: MovieSession, userId: String)

    fun getShoppingCartByUser(userId: String): Mono<ShoppingCart>

    fun registerNewShoppingCart(): Mono<ShoppingCart>

    fun clear(shoppingCart: ShoppingCart)
}
