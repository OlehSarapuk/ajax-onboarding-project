package com.example.ajaxonboardingproject.repository

import com.example.ajaxonboardingproject.model.ShoppingCart
import com.example.ajaxonboardingproject.model.User
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface ShoppingCartRepository : JpaRepository<ShoppingCart, Long> {
    @EntityGraph(attributePaths = ["tickets", "user"])
    fun findByUser(user: User): ShoppingCart
}
