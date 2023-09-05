package com.example.ajaxonboardingproject.repository

import com.example.ajaxonboardingproject.model.MovieSession
import java.time.LocalDateTime

interface MovieSessionRepository {
    fun findByMovieIdAndShowTimeAfter(id: String, date: LocalDateTime): List<MovieSession>

    fun save(movieSession: MovieSession): MovieSession

    fun findById(id: String): MovieSession?

    fun delete(movieSession: MovieSession)
}
