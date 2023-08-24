package com.example.ajaxonboardingproject.service.mapper

import com.example.ajaxonboardingproject.dto.response.OrderResponseDto
import com.example.ajaxonboardingproject.model.Order
import org.springframework.stereotype.Component

@Component
class OrderMapper : ResponseDtoMapper<OrderResponseDto, Order> {
    override fun mapToDto(model: Order): OrderResponseDto {
        return OrderResponseDto(
            id = model.id,
            ticketIds = model.tickets
                .map { it.id }
                .toMutableList(),
            userId = model.user.id,
            orderTime = model.orderTime
        )
    }
}
