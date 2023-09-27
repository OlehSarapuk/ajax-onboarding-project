package com.example.ajaxonboardingproject.application.repository

import com.example.ajaxonboardingproject.domain.CinemaHall
import com.mongodb.client.result.DeleteResult
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface CinemaHallRepositoryOutPort {

    fun save(cinemaHall: CinemaHall): Mono<CinemaHall>

    fun findAll(): Flux<CinemaHall>

    fun findById(id: String): Mono<CinemaHall>

    fun deleteAll(): Mono<DeleteResult>
}
