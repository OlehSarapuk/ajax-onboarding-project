package com.example.ajaxonboardingproject.repository

import com.example.ajaxonboardingproject.model.MovieSession
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

interface MovieSessionRepository : JpaRepository<MovieSession, Long> {
    @Query("from MovieSession m where m.movie.id = :movieId and date_format(m.showTime, '%Y-%m-%d') = :date")
    fun findAvailableSessions(movieId : Long, date : LocalDate) : MutableList<MovieSession?>
}