package com.example.ajaxonboardingproject.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("movies")
data class Movie(
    var title: String,
    var description: String,
) {
    @Id
    lateinit var id: String
}
