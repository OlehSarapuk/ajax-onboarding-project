package com.example.ajaxonboardingproject.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "movies")
data class Movie(
    var title: String,
    var description: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    lateinit var id: java.lang.Long
}
