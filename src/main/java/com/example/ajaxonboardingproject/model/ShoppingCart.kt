package com.example.ajaxonboardingproject.model

import jakarta.persistence.*

@Entity
@Table(name = "shopping_carts")
data class ShoppingCart(
        @OneToMany
        @JoinTable(name = "shopping_carts_tickets",
                joinColumns = [JoinColumn(name = "shopping_cart_id")],
                inverseJoinColumns = [JoinColumn(name = "ticket_id")])
        var tickets : MutableList<Ticket>,
        @MapsId
        @OneToOne
        @JoinColumn(name = "id")
        var user : User,
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id : Long? = null
)