package com.example.ajaxonboardingproject.repository

import com.example.ajaxonboardingproject.model.Movie
import com.example.ajaxonboardingproject.model.MovieSession
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import java.time.LocalDate
import java.time.LocalDateTime

interface MovieSessionRepository : MongoRepository<MovieSession, String> {
    @Query("")
    fun findAvailableSessions(movie: Movie, date: LocalDate): List<MovieSession>
}
