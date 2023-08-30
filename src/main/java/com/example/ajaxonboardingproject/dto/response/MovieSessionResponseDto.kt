package com.example.ajaxonboardingproject.dto.response

import java.time.LocalDateTime

data class MovieSessionResponseDto(
    val movieSessionId: String,
    val movieId: String,
    val movieTitle: String,
    val cinemaHallId: String,
    val showTime: LocalDateTime
)
