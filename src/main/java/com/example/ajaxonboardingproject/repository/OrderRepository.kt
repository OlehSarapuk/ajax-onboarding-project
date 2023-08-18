package com.example.ajaxonboardingproject.repository

import com.example.ajaxonboardingproject.model.Order
import com.example.ajaxonboardingproject.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Order, Long>{
    fun findAllByUser(user : User) : MutableList<Order>
}