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
import reactor.core.publisher.Mono

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
        val user: Mono<User> = userService.findByEmail(email)
        val movieSession: Mono<MovieSession> = movieSessionService.get(movieSessionId)
        Mono.zip(user, movieSession){user, movieSession ->
            shoppingCartService.addSession(movieSession, user.id)
        }.subscribe()
    }

    @GetMapping("/by-user")
    fun getByUser(auth: Authentication): ShoppingCartResponseDto {
        val details = auth.principal as UserDetails
        val email: String = details.username
        return userService.findByEmail(email)
            .flatMap { shoppingCartService.getShoppingCartByUser(it.id) }
            .map { shoppingCartResponseDtoMapper.mapToDto(it) }
            .block()!!
    }
}
