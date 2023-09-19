package com.example.ajaxonboardingproject.config

import com.example.ajaxonboardingproject.nats.grpc.CinemaHallGrpcService
import com.example.ajaxonboardingproject.nats.grpc.MovieGrpcService
import com.example.ajaxonboardingproject.nats.grpc.MovieSessionGrpcService
import io.grpc.Server
import io.grpc.ServerBuilder
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class GrpcServer(
    private val cinemaHallGrpcService: CinemaHallGrpcService,
    private val movieGrpcService: MovieGrpcService,
    private val movieSessionGrpcService: MovieSessionGrpcService
) {
    @PostConstruct
    fun startServer() {
        val server: Server = ServerBuilder
            .forPort(8097)
            .addService(cinemaHallGrpcService)
            .addService(movieGrpcService)
            .addService(movieSessionGrpcService)
            .build()
        Thread {
            server.start()
            server.awaitTermination()
        }.start()
    }
}
