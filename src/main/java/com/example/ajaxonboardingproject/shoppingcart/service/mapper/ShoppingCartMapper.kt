package com.example.ajaxonboardingproject.shoppingcart.service.mapper

import com.example.ajaxonboardingproject.infrastructure.dto.ResponseDtoMapper
import com.example.ajaxonboardingproject.shoppingcart.dto.ShoppingCartResponseDto
import com.example.ajaxonboardingproject.shoppingcart.model.ShoppingCart
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
