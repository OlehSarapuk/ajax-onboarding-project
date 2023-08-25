package com.example.ajaxonboardingproject.repository

import com.example.ajaxonboardingproject.model.CinemaHall
import org.springframework.data.mongodb.repository.MongoRepository

interface CinemaHallRepository : MongoRepository<CinemaHall, String> {
}
