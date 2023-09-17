package com.example.ajaxonboardingproject.nats.grpc

import com.example.ajaxonboardingproject.MovieOuterClass.MovieResponse
import com.example.ajaxonboardingproject.MovieOuterClass.MovieRequest
import com.example.ajaxonboardingproject.MovieServiceGrpc
import com.example.ajaxonboardingproject.service.MovieService
import com.example.ajaxonboardingproject.service.proto.converter.MovieConverter
import io.grpc.stub.StreamObserver
import org.springframework.stereotype.Controller

@Controller
class MovieGrpcService(
    private val movieService: MovieService,
    private val movieConverter: MovieConverter
): MovieServiceGrpc.MovieServiceImplBase() {

    override fun addMovie(
        request: MovieRequest,
        responseObserver: StreamObserver<MovieResponse>
    ) {
        val movie = movieService.add(movieConverter.protoRequestToMovie(request))
        responseObserver.onNext(movieConverter.movieToProtoResponse(movie))
        responseObserver.onCompleted()
    }

    override fun getAllMovies(
        request: MovieRequest,
        responseObserver: StreamObserver<MovieResponse>
    ) {
        movieService.getAll()
            .map { movieConverter.movieToProtoResponse(it) }
            .forEach { responseObserver.onNext(it) }
        responseObserver.onCompleted()
    }
}