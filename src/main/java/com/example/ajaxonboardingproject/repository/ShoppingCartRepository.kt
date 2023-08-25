package com.example.ajaxonboardingproject.repository

import com.example.ajaxonboardingproject.model.ShoppingCart
import com.example.ajaxonboardingproject.model.User
import org.springframework.data.mongodb.repository.MongoRepository

interface ShoppingCartRepository : MongoRepository<ShoppingCart, Long> {
//    @EntityGraph(attributePaths = ["tickets", "user"])
//    fun findByUser(user: User): ShoppingCart
}
