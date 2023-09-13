package com.example.ajaxonboardingproject.repository

import com.example.ajaxonboardingproject.model.CinemaHall
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface CinemaHallRepository {
    fun save(cinemaHall: CinemaHall): Mono<CinemaHall>

    fun findAll(): Flux<CinemaHall>

    fun findById(id: String): Mono<CinemaHall>
}
