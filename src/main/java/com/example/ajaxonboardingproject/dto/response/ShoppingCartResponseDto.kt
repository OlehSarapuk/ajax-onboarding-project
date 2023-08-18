package com.example.ajaxonboardingproject.dto.response

data class ShoppingCartResponseDto(
        var userId : Long,
        var ticketIds : List<Long>
)