package com.example.ajaxonboardingproject.service

import com.example.ajaxonboardingproject.domain.MovieSession
import com.example.ajaxonboardingproject.repository.ShoppingCartRepository
import com.example.ajaxonboardingproject.model.Ticket
import com.example.ajaxonboardingproject.model.ShoppingCart
import com.example.ajaxonboardingproject.model.User
import com.example.ajaxonboardingproject.repository.UserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ShoppingCartServiceImpl(
    private val shoppingCartRepository: ShoppingCartRepository,
    private val userRepository: UserRepository
) : ShoppingCartService {
    override fun addSession(
        movieSession: MovieSession,
        userId: String
    ) {
        val ticket = Ticket(movieSession = movieSession)
        getUserFromDb(userId)
            .flatMap {
                it.shoppingCart.tickets.add(ticket)
                userRepository.save(it)
            }
            .subscribe()
    }

    override fun getShoppingCartByUser(userId: String): Mono<ShoppingCart> {
        return userRepository.findShoppingCartByUserId(userId)
            .switchIfEmpty(Mono.error(NoSuchElementException("Can't get shopping cart for user with id $userId")))
    }

    override fun registerNewShoppingCart(): Mono<ShoppingCart> {
        val shoppingCart = ShoppingCart(
            tickets = mutableListOf()
        )
        return shoppingCartRepository.save(shoppingCart)
    }

    override fun clear(shoppingCart: ShoppingCart) {
        shoppingCart.tickets = mutableListOf()
        shoppingCartRepository.save(shoppingCart)
    }

    fun getUserFromDb(id: String): Mono<User> {
        return userRepository.findById(id)
            .switchIfEmpty(Mono.error(NoSuchElementException("Can't get user with id $id")))
    }
}
