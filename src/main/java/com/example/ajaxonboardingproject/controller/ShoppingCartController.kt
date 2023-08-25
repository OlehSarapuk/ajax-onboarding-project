package com.example.ajaxonboardingproject.controller

import com.example.ajaxonboardingproject.dto.response.ShoppingCartResponseDto
import com.example.ajaxonboardingproject.model.MovieSession
import com.example.ajaxonboardingproject.model.ShoppingCart
import com.example.ajaxonboardingproject.model.User
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
    private val shoppingCartResponseDtoMapper: ResponseDtoMapper<ShoppingCartResponseDto, ShoppingCart>
) {
    @PutMapping("/movie-sessions")
    fun addToCart(
        auth: Authentication,
        @RequestParam movieSessionId: String
    ) {
        val details = auth.principal as UserDetails
        val email: String = details.username
        val user: User = userService.findByEmail(email)
        val movieSession: MovieSession = movieSessionService.get(movieSessionId)
        shoppingCartService.addSession(movieSession, user)
    }

    @GetMapping("/by-user")
    fun getByUser(auth: Authentication): ShoppingCartResponseDto {
        val details = auth.principal as UserDetails
        val email: String = details.username
        val user: User = userService.findByEmail(email)
        return shoppingCartResponseDtoMapper.mapToDto(shoppingCartService.getByUser(user))
    }
}
