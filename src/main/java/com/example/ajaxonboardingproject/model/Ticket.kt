package com.example.ajaxonboardingproject.model

import org.bson.codecs.pojo.annotations.BsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("tickets")
data class Ticket(
    @BsonProperty(value = "movie_session")
    val movieSession: MovieSession,
) {
    @Id
    lateinit var id: String
}
