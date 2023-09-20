package com.example.ajaxonboardingproject.kafka

import com.example.ajaxonboardingproject.CinemaHallKafkaServiceGrpc
import com.example.ajaxonboardingproject.CinemaHallRequest
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
    private lateinit var stub: CinemaHallKafkaServiceGrpc.CinemaHallKafkaServiceBlockingStub

    fun observe() {
        channel = ManagedChannelBuilder
            .forAddress("localhost", grpcPort)
            .usePlaintext()
            .build()
        stub = CinemaHallKafkaServiceGrpc.newBlockingStub(channel)
        val kafkaAddCinemaHall =
            stub.kafkaAddCinemaHall(CinemaHallRequest.getDefaultInstance())
        kafkaAddCinemaHall.forEach { println(it) }
        channel.shutdown()
    }
}
