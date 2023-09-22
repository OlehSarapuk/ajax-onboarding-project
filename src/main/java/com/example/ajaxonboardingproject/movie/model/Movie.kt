package com.example.ajaxonboardingproject.movie.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("movies")
data class Movie(
    val title: String,
    val description: String,
) {
    @Id
    lateinit var id: String
}
