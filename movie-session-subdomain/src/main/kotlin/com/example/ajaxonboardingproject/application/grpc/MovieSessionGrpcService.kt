package com.example.ajaxonboardingproject.application.grpc

import com.example.ajaxonboardingproject.MovieSessionAddRequest
import com.example.ajaxonboardingproject.MovieSessionDeleteRequest
import com.example.ajaxonboardingproject.MovieSessionResponse
import com.example.ajaxonboardingproject.MovieSessionServiceGrpc
import com.example.ajaxonboardingproject.MovieSessionUpdateRequest
import com.example.ajaxonboardingproject.application.service.MovieSessionService
import com.example.ajaxonboardingproject.application.proto.converter.MovieSessionConverter
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
        movieSessionService.add(movieSessionConverter.protoToMovieSession(request.movieSession))
            .doOnNext { responseObserver.onNext(movieSessionConverter.movieSessionToProtoResponse(it)) }
            .block()
        responseObserver.onCompleted()
    }

    override fun updateMovieSession(
        request: MovieSessionUpdateRequest,
        responseObserver: StreamObserver<MovieSessionResponse>
    ) {
        movieSessionService.update(movieSessionConverter.protoToMovieSession(request.movieSession))
            .doOnNext { responseObserver.onNext(movieSessionConverter.movieSessionToProtoResponse(it)) }
            .block()
        responseObserver.onCompleted()
    }

    override fun deleteMovieSession(
        request: MovieSessionDeleteRequest,
        responseObserver: StreamObserver<MovieSessionResponse>
    ) {
        movieSessionService.delete(request.id)
            .doOnNext { responseObserver.onNext(MovieSessionResponse.getDefaultInstance()) }
            .block()
        responseObserver.onCompleted()
    }
}
