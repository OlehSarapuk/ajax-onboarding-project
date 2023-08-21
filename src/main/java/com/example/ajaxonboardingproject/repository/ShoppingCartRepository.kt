package com.example.ajaxonboardingproject.repository

import com.example.ajaxonboardingproject.model.ShoppingCart
import com.example.ajaxonboardingproject.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ShoppingCartRepository : JpaRepository<ShoppingCart, Long> {
    //@Query("from ShoppingCart sc join fetch sc.tickets join fetch sc.user where sc.user = :user")
    fun findByUser(user : User) : ShoppingCart
}