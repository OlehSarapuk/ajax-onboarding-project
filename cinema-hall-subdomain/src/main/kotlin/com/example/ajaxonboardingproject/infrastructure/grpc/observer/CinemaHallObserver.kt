package com.example.ajaxonboardingproject.infrastructure.grpc.observer

import com.example.ajaxonboardingproject.CinemaHallRequest
import com.example.ajaxonboardingproject.ReactorCinemaHallKafkaServiceGrpc
import com.example.ajaxonboardingproject.ReactorCinemaHallKafkaServiceGrpc.ReactorCinemaHallKafkaServiceStub
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class CinemaHallObserver(
    @Value("\${spring.grpc.port}")
    private val grpcPort: Int
) {
    private lateinit var channel: ManagedChannel
    private lateinit var stub: ReactorCinemaHallKafkaServiceStub

    fun observe() {
        channel = ManagedChannelBuilder
            .forAddress("localhost", grpcPort)
            .usePlaintext()
            .build()
        stub = ReactorCinemaHallKafkaServiceGrpc.newReactorStub(channel)
        stub.kafkaAddCinemaHall(Flux.from(Mono.just(CinemaHallRequest.getDefaultInstance())))
            .doOnNext { println(it) }
            .subscribe()
        channel.shutdown()
    }
}
