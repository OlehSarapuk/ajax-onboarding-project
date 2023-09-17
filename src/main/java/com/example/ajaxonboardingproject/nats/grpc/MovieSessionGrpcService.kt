package com.example.ajaxonboardingproject.nats.grpc

import com.example.ajaxonboardingproject.MovieSessionOuterClass.MovieSessionResponse
import com.example.ajaxonboardingproject.MovieSessionOuterClass.MovieSessionRequest
import com.example.ajaxonboardingproject.MovieSessionServiceGrpc
import com.example.ajaxonboardingproject.service.MovieSessionService
import com.example.ajaxonboardingproject.service.proto.converter.MovieSessionConverter
import io.grpc.stub.StreamObserver
import org.springframework.stereotype.Controller

@Controller
class MovieSessionGrpcService(
    private val movieSessionService: MovieSessionService,
    private val movieSessionConverter: MovieSessionConverter
): MovieSessionServiceGrpc.MovieSessionServiceImplBase() {

    override fun addMovieSession(
        request: MovieSessionRequest,
        responseObserver: StreamObserver<MovieSessionResponse>
    ) {
        val movieSession = movieSessionService.add(movieSessionConverter.protoRequestToMovieSession(request))
        responseObserver.onNext(movieSessionConverter.movieSessionToProtoResponse(movieSession))
        responseObserver.onCompleted()
    }

    override fun updateMovieSession(
        request: MovieSessionRequest,
        responseObserver: StreamObserver<MovieSessionResponse>
    ) {
        val movieSession = movieSessionService.update(movieSessionConverter.protoRequestToMovieSession(request))
        responseObserver.onNext(movieSessionConverter.movieSessionToProtoResponse(movieSession))
        responseObserver.onCompleted()
    }

    override fun deleteMovieSession(
        request: MovieSessionRequest,
        responseObserver: StreamObserver<MovieSessionResponse>
    ) {
        movieSessionService.delete(movieSessionConverter.protoRequestToMovieSession(request).id)
        responseObserver.onNext(MovieSessionResponse.newBuilder().build())
        responseObserver.onCompleted()
    }
}