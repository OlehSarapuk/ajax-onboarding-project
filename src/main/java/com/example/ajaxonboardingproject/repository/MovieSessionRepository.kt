package com.example.ajaxonboardingproject.repository

import com.example.ajaxonboardingproject.model.MovieSession
import com.mongodb.client.result.DeleteResult
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime

interface MovieSessionRepository {

    fun findByMovieIdAndShowTimeAfter(id: String, date: LocalDateTime): Flux<MovieSession>

    fun save(movieSession: MovieSession): Mono<MovieSession>

    fun findById(id: String): Mono<MovieSession>

    fun delete(movieSession: MovieSession): Mono<DeleteResult>

    fun findAll(): List<MovieSession>
}
