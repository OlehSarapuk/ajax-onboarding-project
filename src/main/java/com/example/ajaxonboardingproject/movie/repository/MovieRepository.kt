package com.example.ajaxonboardingproject.movie.repository

import com.example.ajaxonboardingproject.movie.model.Movie
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface MovieRepository {

    fun save(movie: Movie): Mono<Movie>

    fun findAll(): Flux<Movie>

    fun findById(id: String): Mono<Movie>
}
