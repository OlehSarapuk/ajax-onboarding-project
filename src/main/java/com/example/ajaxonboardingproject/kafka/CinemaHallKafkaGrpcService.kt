package com.example.ajaxonboardingproject.kafka

import com.example.ajaxonboardingproject.CinemaHallRequest
import com.example.ajaxonboardingproject.CinemaHallResponse
import com.example.ajaxonboardingproject.NatsSubject
import com.example.ajaxonboardingproject.ReactorCinemaHallKafkaServiceGrpc
import io.nats.client.Connection
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class CinemaHallKafkaGrpcService(
    private val connection: Connection
): ReactorCinemaHallKafkaServiceGrpc.CinemaHallKafkaServiceImplBase() {
    private val list: MutableList<CinemaHallResponse> = mutableListOf()

    @PostConstruct
    fun listenToEvents() {
        val dispatcher = connection.createDispatcher { message ->
            list.add(CinemaHallResponse.parseFrom(message.data))
        }
        dispatcher.subscribe(NatsSubject.KAFKA_GET_FRESHLY_ADDED_CINEMA_HALL_SUBJECT)
    }

    override fun kafkaAddCinemaHall(
        request: Mono<CinemaHallRequest>
    ): Flux<CinemaHallResponse> {
        return Flux.fromIterable(list)
    }
}
