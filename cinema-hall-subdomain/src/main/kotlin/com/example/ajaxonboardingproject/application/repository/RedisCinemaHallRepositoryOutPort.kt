package com.example.ajaxonboardingproject.application.repository

import com.example.ajaxonboardingproject.domain.CinemaHall
import reactor.core.publisher.Mono

interface RedisCinemaHallRepositoryOutPort {
    fun findById(id: String): Mono<CinemaHall>

    fun save(cinemaHall: CinemaHall): Mono<CinemaHall>
}