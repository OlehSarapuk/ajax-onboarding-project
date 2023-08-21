package com.example.ajaxonboardingproject.model

import jakarta.persistence.*

@Entity
@Table(name = "tickets")
data class Ticket(
        @ManyToOne
        @JoinColumn(name = "movie_session_id")
        var movieSession: MovieSession,
        @ManyToOne
        @JoinColumn(name = "user_id")
        var user : User,
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id : Long? = null
)