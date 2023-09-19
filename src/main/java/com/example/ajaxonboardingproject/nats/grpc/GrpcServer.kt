package com.example.ajaxonboardingproject.nats.grpc

import com.example.ajaxonboardingproject.kafka.CinemaHallKafkaGrpcService
import io.grpc.Server
import io.grpc.ServerBuilder
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component

@Component
class GrpcServer(
    private val cinemaHallGrpcService: CinemaHallGrpcService,
    private val movieGrpcService: MovieGrpcService,
    private val movieSessionGrpcService: MovieSessionGrpcService,
    private val listener: CinemaHallKafkaGrpcService
) {
    private val server: Server = ServerBuilder
        .forPort(8097)
        .addService(cinemaHallGrpcService)
        .addService(movieGrpcService)
        .addService(movieSessionGrpcService)
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
