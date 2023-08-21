package com.example.ajaxonboardingproject.controller

import com.example.ajaxonboardingproject.dto.response.ShoppingCartResponseDto
import com.example.ajaxonboardingproject.model.ShoppingCart
import com.example.ajaxonboardingproject.service.MovieSessionService
import com.example.ajaxonboardingproject.service.ShoppingCartService
import com.example.ajaxonboardingproject.service.UserService
import com.example.ajaxonboardingproject.service.mapper.ResponseDtoMapper
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/shopping-carts")
data class ShoppingCartController(
        private val shoppingCartService: ShoppingCartService,
        private val movieSessionService: MovieSessionService,
        private val userService: UserService,
        private val shoppingCartResponseDtoMapper: ResponseDtoMapper<ShoppingCartResponseDto, ShoppingCart>) {
    @PutMapping("/movie-sessions")
    fun addToCart(auth : Authentication,
                  @RequestParam movieSessionId : Long) {
        val details = auth.principal as UserDetails
        val email = details.username
        val user = userService.findByEmail(email).orElseThrow{NoSuchElementException("User with email $email not found")}
        val movieSession = movieSessionService.get(movieSessionId)
        shoppingCartService.addSession(movieSession, user)
    }

    @GetMapping("/by-user")
    fun getByUser(auth : Authentication) : ShoppingCartResponseDto {
        val details = auth.principal as UserDetails
        val email = details.username
        val user = userService.findByEmail(email).orElseThrow{NoSuchElementException("User with email $email not found")}
        return shoppingCartResponseDtoMapper.mapToDto(shoppingCartService.getByUser(user))
    }
}