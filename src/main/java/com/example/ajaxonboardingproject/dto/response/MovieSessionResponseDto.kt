package com.example.ajaxonboardingproject.dto.response

import java.time.LocalDateTime

data class MovieSessionResponseDto(
    var movieSessionId: String,
    var movieId: String,
    var movieTitle: String,
    var cinemaHallId: Long,
    var showTime: LocalDateTime
)
