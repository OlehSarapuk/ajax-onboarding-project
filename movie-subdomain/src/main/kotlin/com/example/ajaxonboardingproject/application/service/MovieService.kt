package com.example.ajaxonboardingproject.application.service

import com.example.ajaxonboardingproject.application.repository.MovieRepositoryOutPort
import com.example.ajaxonboardingproject.domain.Movie
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class MovieService(private val movieRepositoryOutPort: MovieRepositoryOutPort) : MovieInPort {
    override fun add(movie: Movie): Mono<Movie> {
        return movieRepositoryOutPort.save(movie)
    }

    override fun get(id: String): Mono<Movie> {
        return movieRepositoryOutPort.findById(id)
            .switchIfEmpty(Mono.error(NoSuchElementException("Can't get movie by id $id")))
    }

    override fun getAll(): Flux<Movie> {
        return movieRepositoryOutPort.findAll()
    }
}
