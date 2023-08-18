package com.example.ajaxonboardingproject.dto.request

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size

data class CinemaHallRequestDto(
        val capacity: @Min(10) Int,
        val description: @Size(max = 200) String)
