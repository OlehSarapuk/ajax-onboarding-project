package com.example.ajaxonboardingproject.service

import com.example.ajaxonboardingproject.model.MovieSession
import java.time.LocalDate

interface MovieSessionService {
    fun findAvailableSessions(movieId : Long, date : LocalDate) : List<MovieSession>

    fun add(session: MovieSession) : MovieSession

    fun get(id : Long) : MovieSession

    fun update(movieSession: MovieSession) : MovieSession

    fun delete(id : Long)
}
