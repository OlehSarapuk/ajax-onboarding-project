package com.example.ajaxonboardingproject.repository.impl

import com.example.ajaxonboardingproject.model.ShoppingCart
import com.example.ajaxonboardingproject.repository.ShoppingCartRepository
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class ShoppingCartRepositoryImpl(
    private val mongoTemplate: ReactiveMongoTemplate
): ShoppingCartRepository {
    override fun save(shoppingCart: ShoppingCart): Mono<ShoppingCart> {
        return mongoTemplate.save(shoppingCart)
    }
}
