package com.example.ajaxonboardingproject.controller

import com.example.ajaxonboardingproject.dto.response.OrderResponseDto
import com.example.ajaxonboardingproject.model.Order
import com.example.ajaxonboardingproject.service.OrderService
import com.example.ajaxonboardingproject.service.ShoppingCartService
import com.example.ajaxonboardingproject.service.UserService
import com.example.ajaxonboardingproject.service.mapper.ResponseDtoMapper
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import java.util.NoSuchElementException

data class OrderController(
        private val shoppingCartService: ShoppingCartService,
        private val orderService: OrderService,
        private val userService: UserService,
        private val orderResponseDtoMapper: ResponseDtoMapper<OrderResponseDto, Order>) {
    @PostMapping("/complete")
    fun completeOrder(auth : Authentication) : OrderResponseDto {
        val email = auth.name
        val user = userService.findByEmail(email).orElseThrow{NoSuchElementException("User with email $email not found")}
        val cart = shoppingCartService.getByUser(user)
        return orderResponseDtoMapper.mapToDto(orderService.completeOrder(cart))
    }

    @GetMapping
    fun getOrderHistory(auth : Authentication) : List<OrderResponseDto> {
        val email = auth.name
        val user = userService.findByEmail(email).orElseThrow{NoSuchElementException("User with email $email not found")}
        return orderService.getOrdersHistory(user)
                .map(orderResponseDtoMapper::mapToDto)
                .toList()
    }
}