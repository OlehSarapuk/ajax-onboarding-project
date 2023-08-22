package com.example.ajaxonboardingproject.controller

import com.example.ajaxonboardingproject.dto.response.OrderResponseDto
import com.example.ajaxonboardingproject.service.OrderService
import com.example.ajaxonboardingproject.service.ShoppingCartService
import com.example.ajaxonboardingproject.service.UserService
import com.example.ajaxonboardingproject.service.mapper.OrderMapper
import com.example.ajaxonboardingproject.service.mapper.mapToDto
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("orders")
data class OrderController(
        private val shoppingCartService: ShoppingCartService,
        private val orderService: OrderService,
        private val userService: UserService,
        private val orderMapper: OrderMapper) {
    @PostMapping("/complete")
    fun completeOrder(auth : Authentication) : OrderResponseDto {
        val email = auth.name
        val user = userService.findByEmail(email)
        val cart = shoppingCartService.getByUser(user)
        return orderMapper.mapToDto(orderService.completeOrder(cart))
    }

    @GetMapping
    fun getOrderHistory(auth : Authentication) : List<OrderResponseDto> {
        val email = auth.name
        val user = userService.findByEmail(email)
        return orderService.getOrdersHistory(user)
                .map(orderMapper::mapToDto)
                .toList()
    }
}
