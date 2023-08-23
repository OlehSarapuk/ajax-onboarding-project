package com.example.ajaxonboardingproject.service.mapper

import com.example.ajaxonboardingproject.dto.response.ShoppingCartResponseDto
import com.example.ajaxonboardingproject.model.ShoppingCart
import org.springframework.stereotype.Component

fun ShoppingCartMapper.mapToDto(model : ShoppingCart) : ShoppingCartResponseDto {
    return ShoppingCartResponseDto(
            model.user.id,
            model.tickets
                    .mapNotNull{it?.id}
                    .toList()
    )
}

@Component
class ShoppingCartMapper
