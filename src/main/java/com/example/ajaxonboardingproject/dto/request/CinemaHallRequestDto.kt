package com.example.ajaxonboardingproject.dto.request

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size

data class CinemaHallRequestDto(
    @field:Min(value = 10)
    val capacity: Int,
    @field:Size(max = 200)
    val description: String
)
