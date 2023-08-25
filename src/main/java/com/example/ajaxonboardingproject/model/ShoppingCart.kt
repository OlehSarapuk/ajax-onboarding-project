package com.example.ajaxonboardingproject.model

data class ShoppingCart(
    var tickets: MutableList<Ticket> = mutableListOf()
)
