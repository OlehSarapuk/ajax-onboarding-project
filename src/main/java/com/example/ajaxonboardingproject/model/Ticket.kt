package com.example.ajaxonboardingproject.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("tickets")
data class Ticket(
    val movieSession: MovieSession,
) {
    @Id
    lateinit var id: String
}
