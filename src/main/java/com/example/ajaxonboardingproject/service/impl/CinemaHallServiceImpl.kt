package com.example.ajaxonboardingproject.service.impl

import com.example.ajaxonboardingproject.model.CinemaHall
import com.example.ajaxonboardingproject.repository.CinemaHallRepository
import com.example.ajaxonboardingproject.service.CinemaHallService
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Service
class CinemaHallServiceImpl(private val cinemaHallRepository: CinemaHallRepository) : CinemaHallService {
    override fun add(cinemaHall: CinemaHall): Mono<CinemaHall> {
        return cinemaHallRepository.save(cinemaHall)
    }

    override fun get(id: String): Mono<CinemaHall> {
        return cinemaHallRepository.findById(id)
            .switchIfEmpty(Mono.error(NoSuchElementException("Can't get cinema hall by id $id")))
    }

    override fun getAll(): Flux<CinemaHall> {
        return cinemaHallRepository.findAll()
    }
}
