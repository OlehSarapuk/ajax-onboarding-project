package com.example.ajaxonboardingproject.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document("movieSessions")
class MovieSession(
    val movie: Movie,
    val cinemaHall: CinemaHall,
    val showTime: LocalDateTime,
) {
    @Id
    lateinit var id: String
}
