package com.example.ajaxonboardingproject.nats.grpc

import com.example.ajaxonboardingproject.MovieSessionAddRequest
import com.example.ajaxonboardingproject.MovieSessionDeleteRequest
import com.example.ajaxonboardingproject.MovieSessionResponse
import com.example.ajaxonboardingproject.MovieSessionServiceGrpc
import com.example.ajaxonboardingproject.MovieSessionUpdateRequest
import com.example.ajaxonboardingproject.service.MovieSessionService
import com.example.ajaxonboardingproject.service.proto.converter.MovieSessionConverter
import io.grpc.stub.StreamObserver
import org.springframework.stereotype.Component

@Component
class MovieSessionGrpcService(
    private val movieSessionService: MovieSessionService,
    private val movieSessionConverter: MovieSessionConverter
): MovieSessionServiceGrpc.MovieSessionServiceImplBase() {

    override fun addMovieSession(
        request: MovieSessionAddRequest,
        responseObserver: StreamObserver<MovieSessionResponse>
    ) {
        val movieSession =
            movieSessionService.add(movieSessionConverter.protoToMovieSession(request.movieSession)).block()!!
        responseObserver.onNext(movieSessionConverter.movieSessionToProtoResponse(movieSession))
        responseObserver.onCompleted()
    }

    override fun updateMovieSession(
        request: MovieSessionUpdateRequest,
        responseObserver: StreamObserver<MovieSessionResponse>
    ) {
        val movieSession =
            movieSessionService.update(movieSessionConverter.protoToMovieSession(request.movieSession)).block()!!
        responseObserver.onNext(movieSessionConverter.movieSessionToProtoResponse(movieSession))
        responseObserver.onCompleted()
    }

    override fun deleteMovieSession(
        request: MovieSessionDeleteRequest,
        responseObserver: StreamObserver<MovieSessionResponse>
    ) {
        movieSessionService.delete(request.id).block()
        responseObserver.onNext(MovieSessionResponse.newBuilder().build())
        responseObserver.onCompleted()
    }
}
