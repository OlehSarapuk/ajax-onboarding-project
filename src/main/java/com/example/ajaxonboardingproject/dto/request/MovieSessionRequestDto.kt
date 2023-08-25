package com.example.ajaxonboardingproject.dto.request

import jakarta.validation.constraints.Min
import org.jetbrains.annotations.NotNull
import java.time.LocalDateTime

data class MovieSessionRequestDto(
    @field:Min(0)
    val movieId: String,
    @field:Min(0)
    val cinemaHallId: String,
    @field:NotNull
    val showTime: LocalDateTime
)
