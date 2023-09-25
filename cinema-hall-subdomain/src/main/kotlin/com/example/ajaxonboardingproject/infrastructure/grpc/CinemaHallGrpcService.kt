package com.example.ajaxonboardingproject.infrastructure.grpc

import com.example.ajaxonboardingproject.CinemaHallRequest
import com.example.ajaxonboardingproject.CinemaHallResponse
import com.example.ajaxonboardingproject.CinemaHallServiceGrpc
import com.example.ajaxonboardingproject.application.proto.converter.CinemaHallConverter
import com.example.ajaxonboardingproject.application.service.CinemaHallService
import io.grpc.stub.StreamObserver
import org.springframework.stereotype.Component

@Component
class CinemaHallGrpcService(
    private val cinemaHallService: CinemaHallService,
    private val cinemaHallConverter: CinemaHallConverter
): CinemaHallServiceGrpc.CinemaHallServiceImplBase() {

    override fun addCinemaHall(
        request: CinemaHallRequest,
        responseObserver: StreamObserver<CinemaHallResponse>
    ) {
        cinemaHallService.add(cinemaHallConverter.protoRequestToCinemaHall(request))
            .doOnNext {responseObserver.onNext(cinemaHallConverter.cinemaHallToProtoResponse(it)) }
            .block()
        responseObserver.onCompleted()
    }

    override fun getAllCinemaHalls(
        request: CinemaHallRequest,
        responseObserver: StreamObserver<CinemaHallResponse>
    ) {
        cinemaHallService.getAll()
            .map { cinemaHallConverter.cinemaHallToProtoResponse(it) }
            .doOnNext { responseObserver.onNext(it) }
            .blockLast()
        responseObserver.onCompleted()
    }
}
