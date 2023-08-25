package com.example.ajaxonboardingproject.service.mapper

import com.example.ajaxonboardingproject.dto.response.ShoppingCartResponseDto
import com.example.ajaxonboardingproject.model.ShoppingCart
import org.springframework.stereotype.Component

@Component
class ShoppingCartMapper : ResponseDtoMapper<ShoppingCartResponseDto, ShoppingCart> {
    override fun mapToDto(model: ShoppingCart): ShoppingCartResponseDto {
        return ShoppingCartResponseDto(
            model.tickets
                .map { it.id }
                .toList()
        )
    }
}
