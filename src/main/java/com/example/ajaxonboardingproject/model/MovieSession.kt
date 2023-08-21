package com.example.ajaxonboardingproject.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "movie_sessions")
class MovieSession(
        @ManyToOne
        @JoinColumn(name = "movie_id")
        var movie : Movie,
        @ManyToOne
        @JoinColumn(name = "cinema_hall_id")
        var cinemaHall : CinemaHall,
        @Column(name = "show_time")
        var showTime : LocalDateTime,
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id : Long? = null
)