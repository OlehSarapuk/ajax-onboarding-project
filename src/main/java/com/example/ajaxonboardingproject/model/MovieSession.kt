package com.example.ajaxonboardingproject.model

import org.bson.codecs.pojo.annotations.BsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document("movie_sessions")
class MovieSession(
    var movie: Movie,
    @BsonProperty(value = "cinema_hall")
    var cinemaHall: CinemaHall,
    @BsonProperty(value = "show_time")
    var showTime: LocalDateTime,
) {
    @Id
    lateinit var id: String
}
