package com.example.ajaxonboardingproject.infrastructure.grpc

import com.example.ajaxonboardingproject.MovieRequest
import com.example.ajaxonboardingproject.MovieResponse
import com.example.ajaxonboardingproject.MovieServiceGrpc
import com.example.ajaxonboardingproject.application.proto.converter.MovieConverter
import com.example.ajaxonboardingproject.application.service.MovieInPort
import io.grpc.stub.StreamObserver
import org.springframework.stereotype.Component

@Component
class MovieGrpcService(
    private val movieInPort: MovieInPort,
    private val movieConverter: MovieConverter
) : MovieServiceGrpc.MovieServiceImplBase() {

    override fun addMovie(
        request: MovieRequest,
        responseObserver: StreamObserver<MovieResponse>
    ) {
        movieInPort.add(movieConverter.protoRequestToMovie(request))
            .doOnNext { responseObserver.onNext(movieConverter.movieToProtoResponse(it)) }
            .block()
        responseObserver.onCompleted()
    }

    override fun getAllMovies(
        request: MovieRequest,
        responseObserver: StreamObserver<MovieResponse>
    ) {
        movieInPort.getAll()
            .map { movieConverter.movieToProtoResponse(it) }
            .doOnNext { responseObserver.onNext(it) }
            .blockLast()
        responseObserver.onCompleted()
    }
}
