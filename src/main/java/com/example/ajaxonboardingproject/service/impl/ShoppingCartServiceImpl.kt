package com.example.ajaxonboardingproject.service.impl

import com.example.ajaxonboardingproject.model.MovieSession
import com.example.ajaxonboardingproject.model.ShoppingCart
import com.example.ajaxonboardingproject.model.Ticket
import com.example.ajaxonboardingproject.model.User
import com.example.ajaxonboardingproject.repository.ShoppingCartRepository
import com.example.ajaxonboardingproject.repository.TicketRepository
import com.example.ajaxonboardingproject.service.ShoppingCartService
import org.springframework.stereotype.Service

@Service
class ShoppingCartServiceImpl(
    private val shoppingCartRepository: ShoppingCartRepository,
    private val ticketRepository: TicketRepository
) : ShoppingCartService {
    override fun addSession(
        movieSession: MovieSession,
        user: User
    ) {
        val ticket = Ticket(movieSession = movieSession)
//        val shoppingCart = shoppingCartRepository.findByUser(user)
        val shoppingCart = ShoppingCart(mutableListOf())
        ticketRepository.save(ticket)
        shoppingCart.tickets.add(ticket)
        shoppingCartRepository.save(shoppingCart)
    }

    override fun getByUser(user: User): ShoppingCart {
//        return shoppingCartRepository.findByUser(user)
        return ShoppingCart(mutableListOf())
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
