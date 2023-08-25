package com.example.ajaxonboardingproject.repository

import com.example.ajaxonboardingproject.model.MovieSession
import org.springframework.data.mongodb.repository.MongoRepository
import java.time.LocalDate

interface MovieSessionRepository : MongoRepository<MovieSession, Long> {
//    @Query("from MovieSession m where m.movie.id = :movieId and date_format(m.showTime, '%Y-%m-%d') = :date")
//    fun findAvailableSessions(movieId: Long, date: LocalDate): List<MovieSession>
}
