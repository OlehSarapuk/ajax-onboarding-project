package com.example.ajaxonboardingproject.service

import com.example.ajaxonboardingproject.model.MovieSession
import com.example.ajaxonboardingproject.model.ShoppingCart
import com.example.ajaxonboardingproject.model.User

interface ShoppingCartService {
    fun addSession(movieSession: MovieSession, user : User)

    fun getByUser(user : User) : ShoppingCart

    fun registerNewShoppingCart(user : User)

    fun clear(shoppingCart: ShoppingCart)
}