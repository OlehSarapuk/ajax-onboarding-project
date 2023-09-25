package com.example.ajaxonboardingproject.service.mapper

import com.example.ajaxonboardingproject.dto.ResponseDtoMapper
import com.example.ajaxonboardingproject.dto.ShoppingCartResponseDto
import com.example.ajaxonboardingproject.model.ShoppingCart
import org.springframework.stereotype.Component

@Component
class ShoppingCartMapper : ResponseDtoMapper<ShoppingCartResponseDto, ShoppingCart> {
    override fun mapToDto(model: ShoppingCart): ShoppingCartResponseDto {
        return ShoppingCartResponseDto(
            model.tickets
                .toList()
        )
    }
}
