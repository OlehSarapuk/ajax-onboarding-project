package com.example.ajaxonboardingproject.shoppingcart.dto

import com.example.ajaxonboardingproject.shoppingcart.model.Ticket

data class ShoppingCartResponseDto(
    val tickets: List<Ticket>
)
