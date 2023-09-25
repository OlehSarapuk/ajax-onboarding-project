package com.example.ajaxonboardingproject.infrastructure.grpc

import com.example.ajaxonboardingproject.CinemaHallRequest
import com.example.ajaxonboardingproject.CinemaHallResponse
import com.example.ajaxonboardingproject.NatsSubject
import com.example.ajaxonboardingproject.ReactorCinemaHallKafkaServiceGrpc
import io.nats.client.Connection
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks

@Component
class CinemaHallKafkaGrpcService(
    private val connection: Connection
): ReactorCinemaHallKafkaServiceGrpc.CinemaHallKafkaServiceImplBase() {
    private val responseSink: Sinks.Many<CinemaHallResponse> = Sinks.many().multicast().onBackpressureBuffer()

    @PostConstruct
    fun listenToEvents() {
        val dispatcher = connection.createDispatcher { message ->
            responseSink.tryEmitNext(CinemaHallResponse.parseFrom(message.data))

        }
        dispatcher.subscribe(NatsSubject.KAFKA_GET_FRESHLY_ADDED_CINEMA_HALL_SUBJECT)
    }

    override fun kafkaAddCinemaHall(
        request: Flux<CinemaHallRequest>
    ): Flux<CinemaHallResponse> {
        return Flux.defer { responseSink.asFlux() }
    }
}
