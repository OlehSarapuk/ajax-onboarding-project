package com.example.ajaxonboardingproject.kafka

import com.example.ajaxonboardingproject.CinemaHallKafkaServiceGrpc
import com.example.ajaxonboardingproject.CinemaHallRequest
import com.example.ajaxonboardingproject.CinemaHallResponse
import io.grpc.stub.StreamObserver
import io.nats.client.Connection
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component

@Component
class NatsKafkaListener(
    private val connection: Connection
): CinemaHallKafkaServiceGrpc.CinemaHallKafkaServiceImplBase() {
    private val subject: String = "kafka"
    private val list: MutableList<CinemaHallResponse> = mutableListOf()

    @PostConstruct
    fun listenToEvents() {
        val dispatcher = connection.createDispatcher { message ->
            list.add(CinemaHallResponse.parseFrom(message.data))
        }
        dispatcher.subscribe(subject)
    }

    override fun kafkaAddCinemaHall(
        request: CinemaHallRequest,
        responseObserver: StreamObserver<CinemaHallResponse>
    ) {
        list.map { responseObserver.onNext(it) }
        list.clear()
        responseObserver.onCompleted()
    }
}
