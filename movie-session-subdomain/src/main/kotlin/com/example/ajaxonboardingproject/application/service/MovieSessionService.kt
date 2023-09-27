package com.example.ajaxonboardingproject.application.service

import com.example.ajaxonboardingproject.domain.MovieSession
import com.example.ajaxonboardingproject.application.repository.MovieSessionRepositoryOutPort
import com.mongodb.client.result.DeleteResult
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Service
class MovieSessionService(
    private val movieSessionRepositoryOutPort: MovieSessionRepositoryOutPort,
) : MovieSessionInPort {
    override fun findAvailableSessions(
        movieId: String,
        date: LocalDateTime
    ): Flux<MovieSession> {
        return movieSessionRepositoryOutPort.findByMovieIdAndShowTimeAfter(movieId, date)
    }

    override fun add(movieSession: MovieSession): Mono<MovieSession> {
        return movieSessionRepositoryOutPort.save(movieSession)
    }

    override fun get(id: String): Mono<MovieSession> {
        return movieSessionRepositoryOutPort.findById(id)
            .switchIfEmpty(Mono.error(NoSuchElementException("Session with id $id not found")))
    }

    override fun update(movieSession: MovieSession): Mono<MovieSession> {
        return movieSessionRepositoryOutPort.save(movieSession)
    }

    override fun delete(id: String): Mono<DeleteResult> {
        return get(id).flatMap { movieSessionRepositoryOutPort.delete(it) }
    }
}
