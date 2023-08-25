package com.example.ajaxonboardingproject.repository

import com.example.ajaxonboardingproject.model.ShoppingCart
import com.example.ajaxonboardingproject.model.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface UserRepository : MongoRepository<User, String> {
    fun findByEmail(email: String): User?

    @Query("{_id: ?0}{shoppingCart: 1}")
    fun findShoppingCartByUserId(userId: String): ShoppingCart?
}
