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

@Service
class ShoppingCartServiceImpl(
    private val shoppingCartRepository: ShoppingCartRepository,
    private val userRepository: UserRepository,
    private val ticketRepository: TicketRepository
) : ShoppingCartService {
    override fun addSession(
        movieSession: MovieSession,
        user: User
    ) {
        val ticket = Ticket(movieSession = movieSession)
        val userFromDb: User= userRepository.findById(user.id).orElseThrow { NoSuchElementException("Can't get user with id ${user.id}")}
        val shoppingCart = userFromDb.shoppingCart
        ticketRepository.save(ticket)
        shoppingCart.tickets.add(ticket)
        user.shoppingCart = shoppingCart
        userRepository.save(user)
    }

    override fun getByUser(user: User): ShoppingCart {
        val userFromDb: User= userRepository.findById(user.id).orElseThrow { NoSuchElementException("Can't get user with id ${user.id}")}
        return userFromDb.shoppingCart
    }

    override fun registerNewShoppingCart(): ShoppingCart {
        val shoppingCart = ShoppingCart(
            tickets = mutableListOf()
        )
        return shoppingCartRepository.save(shoppingCart)
    }

    override fun clear(shoppingCart: ShoppingCart) {
        shoppingCart.tickets = mutableListOf()
        shoppingCartRepository.save(shoppingCart)
    }
}
