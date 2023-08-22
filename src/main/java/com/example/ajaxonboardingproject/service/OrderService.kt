package com.example.ajaxonboardingproject.service

import com.example.ajaxonboardingproject.model.Order
import com.example.ajaxonboardingproject.model.ShoppingCart
import com.example.ajaxonboardingproject.model.User

interface OrderService {
    fun completeOrder(shoppingCart : ShoppingCart) : Order

    fun getOrdersHistory(user : User) : MutableList<Order>
}
