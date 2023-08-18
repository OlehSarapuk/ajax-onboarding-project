package com.example.ajaxonboardingproject.repository

import com.example.ajaxonboardingproject.model.Movie
import org.springframework.data.jpa.repository.JpaRepository

interface MovieRepository : JpaRepository<Movie, Long> {
}