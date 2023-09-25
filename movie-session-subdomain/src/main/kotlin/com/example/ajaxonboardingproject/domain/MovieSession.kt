package com.example.ajaxonboardingproject.domain

import java.time.LocalDateTime

data class MovieSession(
    val movie: Movie,
    val cinemaHall: CinemaHall,
    val showTime: LocalDateTime,
) {
    lateinit var id: String
}
