package com.example.ajaxonboardingproject.service.impl

import com.example.ajaxonboardingproject.model.Movie
import com.example.ajaxonboardingproject.repository.MovieRepository
import com.example.ajaxonboardingproject.service.MovieService
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
    }

    override fun getAll(): Flux<Movie> {
        return movieRepository.findAll()
    }
}
