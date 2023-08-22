package com.example.ajaxonboardingproject.model

import jakarta.persistence.*


@Entity
@Table(name = "cinema_halls")
data class CinemaHall(
        var capacity : Int,
        var description : String,
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id : Long? = null)
