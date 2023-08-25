package com.example.ajaxonboardingproject.dto.request

import org.jetbrains.annotations.NotNull
import java.time.LocalDateTime

data class MovieSessionRequestDto(
    val movieId: String,
    val cinemaHallId: String,
    @field:NotNull
    val showTime: LocalDateTime
)
