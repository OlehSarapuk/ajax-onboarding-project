package com.example.ajaxonboardingproject.application.dto

import java.time.LocalDateTime

data class MovieSessionResponseDto(
    val movieSessionId: String,
    val movieId: String,
    val movieTitle: String,
    val cinemaHallId: String,
    val showTime: LocalDateTime
)
