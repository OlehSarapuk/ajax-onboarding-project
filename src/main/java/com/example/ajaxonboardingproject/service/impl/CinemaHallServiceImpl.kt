package com.example.ajaxonboardingproject.service.impl

import com.example.ajaxonboardingproject.KafkaTopic
import com.example.ajaxonboardingproject.model.CinemaHall
import com.example.ajaxonboardingproject.repository.CinemaHallRepository
import com.example.ajaxonboardingproject.service.CinemaHallService
import com.example.ajaxonboardingproject.service.proto.converter.CinemaHallConverter
import com.google.protobuf.GeneratedMessageV3
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class CinemaHallServiceImpl(
    private val cinemaHallRepository: CinemaHallRepository,
    private val kafkaTemplate: KafkaTemplate<String, GeneratedMessageV3>,
    private val cinemaHallConverter: CinemaHallConverter
) : CinemaHallService {
    override fun add(cinemaHall: CinemaHall): Mono<CinemaHall> {
        val saveCinemaHall = cinemaHallRepository.save(cinemaHall)
        kafkaTemplate.send(
            KafkaTopic.GET_FRESHLY_ADDED_CINEMA_HALLS,
            saveCinemaHall.map { cinemaHallConverter.cinemaHallToProtoResponse(it) }.block()
        )
        return saveCinemaHall
    }

    override fun get(id: String): Mono<CinemaHall> {
        return cinemaHallRepository.findById(id)
            .switchIfEmpty(Mono.error(NoSuchElementException("Can't get cinema hall by id $id")))
    }

    override fun getAll(): Flux<CinemaHall> {
        return cinemaHallRepository.findAll()
    }
}
