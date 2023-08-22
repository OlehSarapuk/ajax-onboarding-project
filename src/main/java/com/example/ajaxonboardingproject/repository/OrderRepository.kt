package com.example.ajaxonboardingproject.repository

import com.example.ajaxonboardingproject.model.Order
import com.example.ajaxonboardingproject.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface OrderRepository : JpaRepository<Order, Long>{
    @Query("from Order o join fetch o.tickets where o.user = :user")
    fun findAllByUser(user : User) : MutableList<Order?>
}