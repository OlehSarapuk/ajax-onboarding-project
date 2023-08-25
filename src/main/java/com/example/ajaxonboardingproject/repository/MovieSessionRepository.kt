package com.example.ajaxonboardingproject.repository

import com.example.ajaxonboardingproject.model.MovieSession
import org.springframework.data.mongodb.repository.MongoRepository
import java.time.LocalDateTime

interface MovieSessionRepository : MongoRepository<MovieSession, String> {
    fun findByMovieIdAndShowTimeAfter(movieId: String, date: LocalDateTime): List<MovieSession>
}
