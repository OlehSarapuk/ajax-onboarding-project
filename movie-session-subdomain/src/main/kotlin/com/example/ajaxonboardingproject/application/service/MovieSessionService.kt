package com.example.ajaxonboardingproject.application.service

import com.example.ajaxonboardingproject.domain.MovieSession
import com.mongodb.client.result.DeleteResult
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime

interface MovieSessionService {
    fun findAvailableSessions(movieId: String, date: LocalDateTime): Flux<MovieSession>

    fun add(movieSession: MovieSession): Mono<MovieSession>

    fun get(id: String): Mono<MovieSession>

    fun update(movieSession: MovieSession): Mono<MovieSession>

    fun delete(id: String): Mono<DeleteResult>
}
