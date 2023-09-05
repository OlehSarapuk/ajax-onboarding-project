package com.example.ajaxonboardingproject.service

import com.example.ajaxonboardingproject.model.MovieSession
import com.example.ajaxonboardingproject.model.ShoppingCart

interface ShoppingCartService {
    fun addSession(movieSession: MovieSession, userId: String)

    fun getShoppingCartByUser(userId: String): ShoppingCart

    fun registerNewShoppingCart(): ShoppingCart

    fun clear(shoppingCart: ShoppingCart)
}
