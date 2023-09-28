package com.example.ajaxonboardingproject.application.service

import com.example.ajaxonboardingproject.KafkaTopic
import com.example.ajaxonboardingproject.application.proto.converter.CinemaHallConverter
import com.example.ajaxonboardingproject.application.repository.CinemaHallRepositoryOutPort
import com.example.ajaxonboardingproject.application.repository.RedisCinemaHallRepositoryOutPort
import com.example.ajaxonboardingproject.domain.CinemaHall
import com.google.protobuf.GeneratedMessageV3
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class CinemaHallService(
    private val cinemaHallRepositoryOutPort: CinemaHallRepositoryOutPort,
    private val redisCinemaHallRepositoryOutPort: RedisCinemaHallRepositoryOutPort,
    private val reactiveKafkaConsumerTemplate: ReactiveKafkaProducerTemplate<String, GeneratedMessageV3>,
    private val cinemaHallConverter: CinemaHallConverter
) : CinemaHallInPort {
    override fun add(cinemaHall: CinemaHall): Mono<CinemaHall> {
        return cinemaHallRepositoryOutPort.save(cinemaHall)
            .flatMap {
                reactiveKafkaConsumerTemplate.send(
                    KafkaTopic.GET_FRESHLY_ADDED_CINEMA_HALLS,
                    cinemaHallConverter.cinemaHallToProtoResponse(it)
                ).thenReturn(it)
            }
    }

    override fun get(id: String): Mono<CinemaHall> {
        return redisCinemaHallRepositoryOutPort.findById(id)
            .switchIfEmpty(
                cinemaHallRepositoryOutPort.findById(id)
                    .flatMap { redisCinemaHallRepositoryOutPort.save(it) }
            )
            .switchIfEmpty(Mono.error(NoSuchElementException("Can't get cinema hall by id $id")))
    }

    override fun getAll(): Flux<CinemaHall> {
        return cinemaHallRepositoryOutPort.findAll()
    }
}
