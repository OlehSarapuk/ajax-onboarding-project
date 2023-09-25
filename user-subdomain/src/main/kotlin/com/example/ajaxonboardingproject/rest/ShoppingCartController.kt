package com.example.ajaxonboardingproject.rest

import com.example.ajaxonboardingproject.application.service.MovieSessionService
import com.example.ajaxonboardingproject.service.UserService
import com.example.ajaxonboardingproject.dto.ResponseDtoMapper
import com.example.ajaxonboardingproject.dto.ShoppingCartResponseDto
import com.example.ajaxonboardingproject.service.ShoppingCartService
import com.example.ajaxonboardingproject.model.ShoppingCart
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
        Mono.zip(
            userService.findByEmail(email),
            movieSessionService.get(movieSessionId)
        ) { user, movieSession ->
            shoppingCartService.addSession(movieSession, user.id)
        }.subscribe()
    }

    @GetMapping("/by-user")
    fun getByUser(auth: Authentication): Mono<ShoppingCartResponseDto> {
        val details = auth.principal as UserDetails
        val email: String = details.username
        return userService.findByEmail(email)
            .flatMap { shoppingCartService.getShoppingCartByUser(it.id) }
            .map { shoppingCartResponseDtoMapper.mapToDto(it) }
    }
}
