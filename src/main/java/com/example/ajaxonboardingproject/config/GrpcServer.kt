package com.example.ajaxonboardingproject.config

import com.example.ajaxonboardingproject.grpc.CinemaHallGrpcService
import com.example.ajaxonboardingproject.grpc.MovieGrpcService
import com.example.ajaxonboardingproject.grpc.MovieSessionGrpcService
import io.grpc.Server
import io.grpc.ServerBuilder
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class GrpcServer(
    private val cinemaHallGrpcService: CinemaHallGrpcService,
    private val movieGrpcService: MovieGrpcService,
    private val movieSessionGrpcService: MovieSessionGrpcService,
    @Value("\${spring.grpc.port}")
    var grpcPort: Int
) {
    @PostConstruct
    fun startServer() {
        val server: Server = ServerBuilder
            .forPort(grpcPort)
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
