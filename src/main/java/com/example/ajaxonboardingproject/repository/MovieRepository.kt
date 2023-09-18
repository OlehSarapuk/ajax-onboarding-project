package com.example.ajaxonboardingproject.repository

import com.example.ajaxonboardingproject.model.Movie
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface MovieRepository {

    fun save(movie: Movie): Mono<Movie>

    fun findAll(): Flux<Movie>

    fun findById(id: String): Mono<Movie>
}
