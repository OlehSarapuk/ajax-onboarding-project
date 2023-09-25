package com.example.ajaxonboardingproject.application.service

import com.example.ajaxonboardingproject.domain.CinemaHall
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface CinemaHallService {
    fun add(cinemaHall: CinemaHall): Mono<CinemaHall>

    fun get(id: String): Mono<CinemaHall>

    fun getAll(): Flux<CinemaHall>
}
