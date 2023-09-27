package com.example.ajaxonboardingproject.application.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size
import java.io.Serializable

@Suppress("SerialVersionUIDInSerializableClass")
data class CinemaHallRequestDto(
    @field:Min(value = 10)
    val capacity: Int,
    @field:Size(max = 200)
    val description: String
) : Serializable
