package com.example.ajaxonboardingproject.repository

import com.example.ajaxonboardingproject.model.ShoppingCart
import org.springframework.data.mongodb.repository.MongoRepository

interface ShoppingCartRepository : MongoRepository<ShoppingCart, String> {
//    fun findByUser(user: User): ShoppingCart
}
