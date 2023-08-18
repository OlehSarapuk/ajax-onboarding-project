package com.example.ajaxonboardingproject.service.impl

import com.example.ajaxonboardingproject.model.Order
import com.example.ajaxonboardingproject.model.ShoppingCart
import com.example.ajaxonboardingproject.model.User
import com.example.ajaxonboardingproject.repository.OrderRepository
import com.example.ajaxonboardingproject.service.OrderService
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class OrderServiceImpl(
        private val orderRepository: OrderRepository,
        private val shoppingCartService: ShoppingCartServiceImpl) : OrderService{
    override fun completeOrder(shoppingCart: ShoppingCart): Order {
        val order = Order()
        order.setTickets(shoppingCart.tickets)
        order.user = shoppingCart.user
        orderRepository.save(order)
        shoppingCartService.clear(shoppingCart)
        return order
    }

    override fun getOrdersHistory(user: User): MutableList<Order> {
        return orderRepository.findAllByUser(user)
    }
}