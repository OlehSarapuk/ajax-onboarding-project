package com.example.ajaxonboardingproject.service.impl

import com.example.ajaxonboardingproject.model.MovieSession
import com.example.ajaxonboardingproject.model.ShoppingCart
import com.example.ajaxonboardingproject.model.Ticket
import com.example.ajaxonboardingproject.model.User
import com.example.ajaxonboardingproject.repository.ShoppingCartRepository
import com.example.ajaxonboardingproject.repository.TicketRepository
import com.example.ajaxonboardingproject.repository.UserRepository
import com.example.ajaxonboardingproject.service.ShoppingCartService
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ShoppingCartServiceImpl(
    private val shoppingCartRepository: ShoppingCartRepository,
    private val userRepository: UserRepository,
    private val ticketRepository: TicketRepository
) : ShoppingCartService {
    override fun addSession(
        movieSession: MovieSession,
        userId: String
    ) {
        val ticket = Ticket(movieSession = movieSession)
        ticketRepository.save(ticket)
        lateinit var user: User
        getUserFromDb(userId).map { user = it }
        user.shoppingCart.tickets.add(ticket)
        userRepository.save(user)
    }

    override fun getShoppingCartByUser(userId: String): Mono<ShoppingCart> {
        return userRepository.findShoppingCartByUserId(userId)
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
    }
}
