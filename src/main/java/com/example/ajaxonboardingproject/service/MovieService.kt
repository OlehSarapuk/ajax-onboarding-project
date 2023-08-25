package com.example.ajaxonboardingproject.service

import com.example.ajaxonboardingproject.model.Movie

interface MovieService {
    fun add(movie: Movie): Movie

    fun get(id: String): Movie

    fun getAll(): List<Movie>
}
