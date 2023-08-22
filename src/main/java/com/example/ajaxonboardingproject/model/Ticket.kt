package com.example.ajaxonboardingproject.model

import jakarta.persistence.*


@Entity
@Table(name = "tickets")
data class Ticket(
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "movie_session_id")
        var movieSession: MovieSession,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        var user : User,
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id : Long? = null
)