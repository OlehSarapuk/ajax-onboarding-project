package com.example.ajaxonboardingproject.service.impl

import com.example.ajaxonboardingproject.model.MovieSession
import com.example.ajaxonboardingproject.repository.MovieSessionRepository
import com.example.ajaxonboardingproject.service.MovieSessionService
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class MovieSessionServiceImpl(
    private val movieSessionRepository: MovieSessionRepository,
) : MovieSessionService {
    override fun findAvailableSessions(
        movieId: String,
        date: LocalDateTime
    ): List<MovieSession> {
        return movieSessionRepository.findByMovieIdAndShowTimeAfter(movieId, date)
    }

    override fun add(session: MovieSession): MovieSession {
        return movieSessionRepository.save(session)
    }

    override fun get(id: String): MovieSession {
        return movieSessionRepository.findById(id).orElseThrow {
            NoSuchElementException("Session with id $id not found")
        }
    }

    override fun update(movieSession: MovieSession): MovieSession {
        return movieSessionRepository.save(movieSession)
    }

    override fun delete(id: String) {
        movieSessionRepository.deleteById(id)
    }
}
