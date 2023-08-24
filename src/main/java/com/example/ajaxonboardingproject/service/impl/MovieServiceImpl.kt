package com.example.ajaxonboardingproject.service.impl

import com.example.ajaxonboardingproject.model.Movie
import com.example.ajaxonboardingproject.repository.MovieRepository
import com.example.ajaxonboardingproject.service.MovieService
import org.springframework.stereotype.Service
import java.util.NoSuchElementException

@Service
class MovieServiceImpl(private val movieRepository: MovieRepository) : MovieService {
    override fun add(movie: Movie): Movie {
        return movieRepository.save(movie)
    }

    override fun get(id: Long): Movie {
        return movieRepository.findById(id).orElseThrow {
            NoSuchElementException("Can't get movie by id $id")
        }
    }

    override fun getAll(): List<Movie> {
        return movieRepository.findAll()
    }
}
