package com.example.ajaxonboardingproject.model

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.MapsId
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Table


@Entity
@Table(name = "shopping_carts")
data class ShoppingCart(
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "shopping_carts_tickets",
        joinColumns = [JoinColumn(name = "shopping_cart_id")],
        inverseJoinColumns = [JoinColumn(name = "ticket_id")]
    )
    var tickets: MutableList<Ticket>,
    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    var user: User,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    lateinit var id: java.lang.Long
}
