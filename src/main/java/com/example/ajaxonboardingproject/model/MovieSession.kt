package com.example.ajaxonboardingproject.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "movie_sessions")
class MovieSession(
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "movie_id")
        var movie : Movie,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "cinema_hall_id")
        var cinemaHall : CinemaHall,
        @Column(name = "show_time")
        var showTime : LocalDateTime,
) {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        lateinit var id : java.lang.Long
}
