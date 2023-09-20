package com.example.ajaxonboardingproject.config

import com.example.ajaxonboardingproject.kafka.CinemaHallKafkaGrpcService
import io.grpc.Server
import io.grpc.ServerBuilder
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class GrpcServer(
    private val cinemaHallKafkaGrpcService: CinemaHallKafkaGrpcService,
    @Value("\${spring.grpc.port}")
    var grpcPort: Int
) {
    @PostConstruct
    fun startServer() {
        val server: Server = ServerBuilder
            .forPort(grpcPort)
            .addService(cinemaHallKafkaGrpcService)
            .build()
        Thread {
            server.start()
            server.awaitTermination()
        }.start()
    }
}
