package com.example.ajaxonboardingproject.infrastructure.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("movies")
data class MovieEntity(
    val title: String,
    val description: String,
) {
    @Id
    lateinit var id: String
}
