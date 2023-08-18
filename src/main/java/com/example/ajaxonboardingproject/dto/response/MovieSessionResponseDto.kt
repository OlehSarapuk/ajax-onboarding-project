package com.example.ajaxonboardingproject.dto.response

import java.time.LocalDateTime

data class MovieSessionResponseDto(
        var movieSessionId : Long,
        var movieId : Long,
        var movieTitle : String,
        var cinemaHallId : Long,
        var showTime : LocalDateTime)
