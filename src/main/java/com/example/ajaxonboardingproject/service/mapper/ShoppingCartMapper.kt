package com.example.ajaxonboardingproject.service.mapper

import com.example.ajaxonboardingproject.dto.response.ShoppingCartResponseDto
import com.example.ajaxonboardingproject.model.ShoppingCart
import com.example.ajaxonboardingproject.model.Ticket
import org.springframework.stereotype.Component

@Component
class ShoppingCartMapper : ResponseDtoMapper<ShoppingCartResponseDto, ShoppingCart> {
    override fun mapToDto(model : ShoppingCart): ShoppingCartResponseDto {
        return ShoppingCartResponseDto(
                model.user.id!!,
                model.tickets
                        .stream()
                        .map{it.id!!}
                        .toList())
    }
}
