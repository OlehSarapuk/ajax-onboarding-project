package com.example.ajaxonboardingproject.config

import com.example.ajaxonboardingproject.kafka.CinemaHallKafkaGrpcService
import io.grpc.Server
import io.grpc.ServerBuilder
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class GrpcServer(
    private val listener: CinemaHallKafkaGrpcService,
    @Value("\${spring.grpc.port}")
    private val grpcPort: Int
) {
    private val server: Server = ServerBuilder
        .forPort(grpcPort)
        .addService(listener)
        .build()

    private val thread = Thread {
        server.start()
        server.awaitTermination()
    }

    @PostConstruct
    fun startThread() {
        thread.start()
    }
}
