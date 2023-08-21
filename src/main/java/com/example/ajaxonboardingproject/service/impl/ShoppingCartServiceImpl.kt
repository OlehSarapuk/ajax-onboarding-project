package com.example.ajaxonboardingproject.service.impl

import com.example.ajaxonboardingproject.model.MovieSession
import com.example.ajaxonboardingproject.model.ShoppingCart
import com.example.ajaxonboardingproject.model.Ticket
import com.example.ajaxonboardingproject.model.User
import com.example.ajaxonboardingproject.repository.ShoppingCartRepository
import com.example.ajaxonboardingproject.repository.TicketRepository
import com.example.ajaxonboardingproject.repository.UserRepository
import com.example.ajaxonboardingproject.service.ShoppingCartService
import com.example.ajaxonboardingproject.service.UserService
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class ShoppingCartServiceImpl(
        private val shoppingCartRepository: ShoppingCartRepository,
        private val ticketRepository: TicketRepository,
        private val userService: UserService) : ShoppingCartService {
    override fun addSession(movieSession: MovieSession, user: User) {
        val ticket = Ticket(
                movieSession = movieSession,
                user = user)
        val shoppingCart = shoppingCartRepository.findByUser(user)
        ticketRepository.save(ticket)
        shoppingCart.tickets?.add(ticket)
        shoppingCartRepository.save(shoppingCart)
    }

    override fun getByUser(user: User): ShoppingCart {
        return shoppingCartRepository.findByUser(user)
    }

    override fun registerNewShoppingCart(user: User) {
        val shoppingCart = ShoppingCart(
                user = user,
                tickets = mutableListOf())
        shoppingCartRepository.save(shoppingCart)
    }

    override fun clear(shoppingCart: ShoppingCart) {
        shoppingCart.tickets = mutableListOf() // was null
        shoppingCartRepository.save(shoppingCart)
    }
}