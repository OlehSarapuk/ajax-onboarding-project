package com.example.ajaxonboardingproject.kafka

import com.example.ajaxonboardingproject.CinemaHallRequest
import com.example.ajaxonboardingproject.ReactorCinemaHallKafkaServiceGrpc
import com.example.ajaxonboardingproject.ReactorCinemaHallKafkaServiceGrpc.ReactorCinemaHallKafkaServiceStub
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

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
        stub.kafkaAddCinemaHall(CinemaHallRequest.getDefaultInstance())
                .doOnNext { println(it) }
                .subscribe()
        channel.shutdown()
    }
}
