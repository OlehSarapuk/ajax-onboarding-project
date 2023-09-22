package com.example.ajaxonboardingproject.shoppingcart.service

import com.example.ajaxonboardingproject.moviesession.model.MovieSession
import com.example.ajaxonboardingproject.shoppingcart.model.ShoppingCart
import reactor.core.publisher.Mono

interface ShoppingCartService {
    fun addSession(movieSession: MovieSession, userId: String)

    fun getShoppingCartByUser(userId: String): Mono<ShoppingCart>

    fun registerNewShoppingCart(): Mono<ShoppingCart>

    fun clear(shoppingCart: ShoppingCart)
}
