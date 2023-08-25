package com.example.ajaxonboardingproject.repository

import com.example.ajaxonboardingproject.model.Movie
import org.springframework.data.mongodb.repository.MongoRepository

interface MovieRepository : MongoRepository<Movie, String>
