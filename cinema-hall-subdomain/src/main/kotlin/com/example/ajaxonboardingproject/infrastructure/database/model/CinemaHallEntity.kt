package com.example.ajaxonboardingproject.infrastructure.database.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("cinemaHalls")
data class CinemaHallEntity(
    val capacity: Int,
    val description: String,
) {
    @Id
    lateinit var id: String
}
