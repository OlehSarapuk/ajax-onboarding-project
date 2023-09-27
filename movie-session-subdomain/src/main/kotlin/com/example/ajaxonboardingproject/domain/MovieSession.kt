package com.example.ajaxonboardingproject.domain

import java.time.LocalDateTime

data class MovieSession(
    val id: String?,
    val movie: Movie,
    val cinemaHall: CinemaHall,
    val showTime: LocalDateTime
)
