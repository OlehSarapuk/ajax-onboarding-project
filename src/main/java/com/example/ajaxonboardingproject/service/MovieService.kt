package com.example.ajaxonboardingproject.service

import com.example.ajaxonboardingproject.model.Movie
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface MovieService {
    fun add(movie: Movie): Mono<Movie>

    fun get(id: String): Mono<Movie>

    fun getAll(): Flux<Movie>
}
