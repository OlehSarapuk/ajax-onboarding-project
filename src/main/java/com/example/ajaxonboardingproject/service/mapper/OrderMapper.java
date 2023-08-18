package com.example.ajaxonboardingproject.service.mapper;

import com.example.ajaxonboardingproject.dto.response.OrderResponseDto;
import com.example.ajaxonboardingproject.model.Order;
import com.example.ajaxonboardingproject.model.Ticket;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper implements ResponseDtoMapper<OrderResponseDto, Order> {
    @Override
    public OrderResponseDto mapToDto(Order order) {
        return new OrderResponseDto(
                order.getId(),
                order.getTickets()
                        .stream()
                        .map(Ticket::getId)
                        .toList(),
                order.getUser().getId(),
                order.getOrderTime());
    }
}
