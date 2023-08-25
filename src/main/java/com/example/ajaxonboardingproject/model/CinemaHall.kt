package com.example.ajaxonboardingproject.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("cinema_halls")
data class CinemaHall(
    var capacity: Int,
    var description: String,
) {
    @Id
    lateinit var id: String
}
