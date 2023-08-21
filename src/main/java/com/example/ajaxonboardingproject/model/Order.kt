package com.example.ajaxonboardingproject.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
data class Order(
        @OneToMany
        @JoinTable(name = "orders_tickets",
                joinColumns = [JoinColumn(name = "order_id")],
                inverseJoinColumns = [JoinColumn(name = "ticket_id")])
        var tickets : MutableList<Ticket?>,
        @Column(name = "order_time")
        var orderTime : LocalDateTime,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        var user : User,
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id : Long? = null
        )