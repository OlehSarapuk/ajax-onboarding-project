package com.example.ajaxonboardingproject.shoppingcart.repository

import com.example.ajaxonboardingproject.shoppingcart.model.ShoppingCart
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class ShoppingCartRepositoryImpl(
    private val mongoTemplate: ReactiveMongoTemplate
) : ShoppingCartRepository {

    override fun save(shoppingCart: ShoppingCart): Mono<ShoppingCart> {
        return mongoTemplate.save(shoppingCart)
    }
}
