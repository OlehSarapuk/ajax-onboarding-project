package com.example.ajaxonboardingproject.repository

import com.example.ajaxonboardingproject.model.ShoppingCart
import com.example.ajaxonboardingproject.model.User

interface UserRepository {
    fun findByEmail(email: String): User?

    fun findShoppingCartByUserId(id: String): ShoppingCart?

    fun findById(id: String): User?

    fun save(user: User): User
}
