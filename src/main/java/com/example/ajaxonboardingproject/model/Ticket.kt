package com.example.ajaxonboardingproject.model

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table


@Entity
@Table(name = "tickets")
data class Ticket(
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "movie_session_id")
        var movieSession: MovieSession,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        var user : User,
) {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        lateinit var id : java.lang.Long
}
