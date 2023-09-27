package com.example.ajaxonboardingproject.infrastructure.database.model

import com.example.ajaxonboardingproject.domain.CinemaHall
import com.example.ajaxonboardingproject.domain.Movie
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document("movieSessions")
data class MovieSessionEntity(
    val movie: Movie,
    val cinemaHall: CinemaHall,
    val showTime: LocalDateTime,
) {
    @Id
    lateinit var id: String
}
