package com.example.ajaxonboardingproject.shoppingcart.model

data class ShoppingCart(
    var tickets: MutableList<Ticket> = mutableListOf()
)
