package com.example.ajaxonboardingproject.movie.service.grpc

import com.example.ajaxonboardingproject.MovieRequest
import com.example.ajaxonboardingproject.MovieResponse
import com.example.ajaxonboardingproject.MovieServiceGrpc
import com.example.ajaxonboardingproject.movie.service.proto.converter.MovieConverter
import com.example.ajaxonboardingproject.movie.service.MovieService
import io.grpc.stub.StreamObserver
import org.springframework.stereotype.Component

@Component
class MovieGrpcService(
    private val movieService: MovieService,
    private val movieConverter: MovieConverter
): MovieServiceGrpc.MovieServiceImplBase() {

    override fun addMovie(
        request: MovieRequest,
        responseObserver: StreamObserver<MovieResponse>
    ) {
        movieService.add(movieConverter.protoRequestToMovie(request))
            .doOnNext { responseObserver.onNext(movieConverter.movieToProtoResponse(it)) }
            .block()
        responseObserver.onCompleted()
    }

    override fun getAllMovies(
        request: MovieRequest,
        responseObserver: StreamObserver<MovieResponse>
    ) {
        movieService.getAll()
            .map { movieConverter.movieToProtoResponse(it) }
            .doOnNext { responseObserver.onNext(it) }
            .blockLast()
        responseObserver.onCompleted()
    }
}
