package com.example.ajaxonboardingproject.moviesession.dto

import org.jetbrains.annotations.NotNull
import java.io.Serializable
import java.time.LocalDateTime

@Suppress("SerialVersionUIDInSerializableClass")
data class MovieSessionRequestDto(
    val movieId: String,
    val cinemaHallId: String,
    @field:NotNull
    val showTime: LocalDateTime
): Serializable
