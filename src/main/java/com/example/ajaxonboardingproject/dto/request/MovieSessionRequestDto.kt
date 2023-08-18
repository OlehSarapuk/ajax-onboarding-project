package com.example.ajaxonboardingproject.dto.request

import jakarta.validation.constraints.Min
import java.time.LocalDateTime

data class MovieSessionRequestDto(
        val movieId : @Min(0) Long,
        val cinemaHallId : @Min(0) Long,
        val showTime : LocalDateTime //@NotNull
)