package com.example.ajaxonboardingproject.repository

import com.example.ajaxonboardingproject.model.CinemaHall
import org.springframework.data.jpa.repository.JpaRepository

interface CinemaHallRepository : JpaRepository<CinemaHall, Long> {
}