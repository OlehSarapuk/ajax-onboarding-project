package com.example.ajaxonboardingproject.moviesession.dto

import java.time.LocalDateTime

data class MovieSessionResponseDto(
    val movieSessionId: String,
    val movieId: String,
    val movieTitle: String,
    val cinemaHallId: String,
    val showTime: LocalDateTime
)
