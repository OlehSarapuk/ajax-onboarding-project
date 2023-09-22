package com.example.ajaxonboardingproject.shoppingcart.repository

import com.example.ajaxonboardingproject.shoppingcart.model.ShoppingCart
import reactor.core.publisher.Mono

interface ShoppingCartRepository {

    fun save(shoppingCart: ShoppingCart): Mono<ShoppingCart>
}
