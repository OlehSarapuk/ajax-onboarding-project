package com.example.ajaxonboardingproject.controller

import com.example.ajaxonboardingproject.MovieSessionOuterClass.MovieSessionResponse
import com.example.ajaxonboardingproject.MovieSessionOuterClass.MovieSessionRequest
import com.example.ajaxonboardingproject.model.MovieSession
import com.example.ajaxonboardingproject.service.MovieSessionService
import com.example.ajaxonboardingproject.service.proto.converter.MovieSessionConverter
import com.google.protobuf.Parser
import io.nats.client.Connection
import org.springframework.stereotype.Component

@Component
class NatsMovieSessionAddController(
    private val service: MovieSessionService,
    private val converter: MovieSessionConverter,
    override val connection: Connection
) : NatsController<MovieSessionRequest, MovieSessionResponse> {
    override val subject: String = "movieSession.add"
    override val parser: Parser<MovieSessionRequest> =
        MovieSessionRequest.parser()

    override fun generateReplyForNatsRequest(
        request: MovieSessionRequest
    ): MovieSessionResponse {
        val movieSession: MovieSession =
            service.add(converter.protoRequestToMovieSession(request))
        return converter.movieSessionToProtoResponse(movieSession)
    }
}

@Component
class NatsMovieSessionUpdateController(
    private val service: MovieSessionService,
    private val converter: MovieSessionConverter,
    override val connection: Connection
) : NatsController<MovieSessionRequest, MovieSessionResponse> {
    override val subject: String = "movieSession.update.*"
    override val parser: Parser<MovieSessionRequest> =
        MovieSessionRequest.parser()

    override fun generateReplyForNatsRequest(
        request: MovieSessionRequest
    ): MovieSessionResponse {
        val movieSession: MovieSession = converter.protoRequestToMovieSession(request)
        movieSession.id = subject.split(".").last()
        service.update(movieSession)
        return converter.movieSessionToProtoResponse(movieSession)
    }
}

@Component
class NatsMovieSessionDeleteController(
    private val service: MovieSessionService,
    override val connection: Connection
) : NatsController<MovieSessionRequest, MovieSessionResponse> {
    override val subject: String = "movieSession.delete.*"
    override val parser: Parser<MovieSessionRequest> =
        MovieSessionRequest.parser()

    override fun generateReplyForNatsRequest(
        request: MovieSessionRequest
    ): MovieSessionResponse {
        service.delete(subject.split(".").last())
        return MovieSessionResponse.newBuilder().build()
    }
}
