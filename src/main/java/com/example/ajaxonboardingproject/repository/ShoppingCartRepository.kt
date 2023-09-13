package com.example.ajaxonboardingproject.repository

import com.example.ajaxonboardingproject.model.ShoppingCart
import reactor.core.publisher.Mono

interface ShoppingCartRepository {
    fun save(shoppingCart: ShoppingCart): Mono<ShoppingCart>
}
