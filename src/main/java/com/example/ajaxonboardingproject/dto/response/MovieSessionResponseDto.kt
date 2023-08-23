package com.example.ajaxonboardingproject.dto.response

import java.time.LocalDateTime

data class MovieSessionResponseDto(
        var movieSessionId: java.lang.Long,
        var movieId: java.lang.Long,
        var movieTitle: String,
        var cinemaHallId: java.lang.Long,
        var showTime: LocalDateTime)
