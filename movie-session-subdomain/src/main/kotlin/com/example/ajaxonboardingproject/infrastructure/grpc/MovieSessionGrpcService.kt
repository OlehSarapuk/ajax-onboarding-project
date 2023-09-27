package com.example.ajaxonboardingproject.infrastructure.grpc

import com.example.ajaxonboardingproject.MovieSessionAddRequest
import com.example.ajaxonboardingproject.MovieSessionDeleteRequest
import com.example.ajaxonboardingproject.MovieSessionResponse
import com.example.ajaxonboardingproject.MovieSessionServiceGrpc
import com.example.ajaxonboardingproject.MovieSessionUpdateRequest
import com.example.ajaxonboardingproject.application.service.MovieSessionInPort
import com.example.ajaxonboardingproject.application.proto.converter.MovieSessionConverter
import io.grpc.stub.StreamObserver
import org.springframework.stereotype.Component

@Component
class MovieSessionGrpcService(
    private val movieSessionInPort: MovieSessionInPort,
    private val movieSessionConverter: MovieSessionConverter
): MovieSessionServiceGrpc.MovieSessionServiceImplBase() {

    override fun addMovieSession(
        request: MovieSessionAddRequest,
        responseObserver: StreamObserver<MovieSessionResponse>
    ) {
        movieSessionInPort.add(movieSessionConverter.protoToMovieSession(request.movieSession))
            .doOnNext { responseObserver.onNext(movieSessionConverter.movieSessionToProtoResponse(it)) }
            .block()
        responseObserver.onCompleted()
    }

    override fun updateMovieSession(
        request: MovieSessionUpdateRequest,
        responseObserver: StreamObserver<MovieSessionResponse>
    ) {
        movieSessionInPort.update(movieSessionConverter.protoToMovieSession(request.movieSession))
            .doOnNext { responseObserver.onNext(movieSessionConverter.movieSessionToProtoResponse(it)) }
            .block()
        responseObserver.onCompleted()
    }

    override fun deleteMovieSession(
        request: MovieSessionDeleteRequest,
        responseObserver: StreamObserver<MovieSessionResponse>
    ) {
        movieSessionInPort.delete(request.id)
            .doOnNext { responseObserver.onNext(MovieSessionResponse.getDefaultInstance()) }
            .block()
        responseObserver.onCompleted()
    }
}
