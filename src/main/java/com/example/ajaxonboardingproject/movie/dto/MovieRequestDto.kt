package com.example.ajaxonboardingproject.movie.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.io.Serializable

@Suppress("SerialVersionUIDInSerializableClass")
data class MovieRequestDto(
    @field:NotBlank
    val title: String,
    @field:Size(max = 200)
    val description: String
): Serializable
