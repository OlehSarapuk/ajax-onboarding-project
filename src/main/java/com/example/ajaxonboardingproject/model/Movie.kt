package com.example.ajaxonboardingproject.model

import jakarta.persistence.*

@Entity
@Table(name = "movies")
data class Movie(
        var title : String,
        var description : String,
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id : Long? = null
)
