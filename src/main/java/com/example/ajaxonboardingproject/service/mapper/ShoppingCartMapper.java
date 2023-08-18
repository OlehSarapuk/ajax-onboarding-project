package com.example.ajaxonboardingproject.service.mapper;

import com.example.ajaxonboardingproject.dto.response.ShoppingCartResponseDto;
import com.example.ajaxonboardingproject.model.ShoppingCart;
import com.example.ajaxonboardingproject.model.Ticket;
import org.springframework.stereotype.Component;

@Component
public class ShoppingCartMapper implements
        ResponseDtoMapper<ShoppingCartResponseDto, ShoppingCart> {

    @Override
    public ShoppingCartResponseDto mapToDto(ShoppingCart shoppingCart) {
        return new ShoppingCartResponseDto(
                shoppingCart.getUser().getId(),
                shoppingCart.getTickets()
                        .stream()
                        .map(Ticket::getId)
                        .toList());
    }
}
