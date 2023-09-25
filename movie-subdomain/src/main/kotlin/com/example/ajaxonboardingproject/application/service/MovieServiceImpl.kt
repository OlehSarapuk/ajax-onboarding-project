package com.example.ajaxonboardingproject.application.service

import com.example.ajaxonboardingproject.application.repository.MovieRepository
import com.example.ajaxonboardingproject.domain.Movie
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class MovieServiceImpl(private val movieRepository: MovieRepository) : MovieService {
    override fun add(movie: Movie): Mono<Movie> {
        return movieRepository.save(movie)
    }

    override fun get(id: String): Mono<Movie> {
        return movieRepository.findById(id)
            .switchIfEmpty(Mono.error(NoSuchElementException("Can't get movie by id $id")))
    }

    override fun getAll(): Flux<Movie> {
        return movieRepository.findAll()
    }
}
