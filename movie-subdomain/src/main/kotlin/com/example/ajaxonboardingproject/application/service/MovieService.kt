package com.example.ajaxonboardingproject.application.service

import com.example.ajaxonboardingproject.domain.Movie
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface MovieService {
    fun add(movie: Movie): Mono<Movie>

    fun get(id: String): Mono<Movie>

    fun getAll(): Flux<Movie>
}

