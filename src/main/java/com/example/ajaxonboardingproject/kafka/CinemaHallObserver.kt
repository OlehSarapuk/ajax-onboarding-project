package com.example.ajaxonboardingproject.kafka

import com.example.ajaxonboardingproject.CinemaHallKafkaServiceGrpc
import com.example.ajaxonboardingproject.CinemaHallRequest
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder

class CinemaHallObserver {
    private lateinit var channel: ManagedChannel
    private lateinit var stub: CinemaHallKafkaServiceGrpc.CinemaHallKafkaServiceBlockingStub

    fun observe() {
        channel = ManagedChannelBuilder
            .forAddress("localhost", 8097)
            .usePlaintext()
            .build()
        stub = CinemaHallKafkaServiceGrpc.newBlockingStub(channel)

        val kafkaAddCinemaHall = stub.kafkaAddCinemaHall(CinemaHallRequest.getDefaultInstance())

        kafkaAddCinemaHall.forEach { println(it) }

        channel.shutdown()
    }
}