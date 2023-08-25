package com.example.ajaxonboardingproject.service

import com.example.ajaxonboardingproject.model.MovieSession
import java.time.LocalDate
import java.time.LocalDateTime

interface MovieSessionService {
    fun findAvailableSessions(movieId: String, date: LocalDate): List<MovieSession>

    fun add(session: MovieSession): MovieSession

    fun get(id: String): MovieSession

    fun update(movieSession: MovieSession): MovieSession

    fun delete(id: String)
}
