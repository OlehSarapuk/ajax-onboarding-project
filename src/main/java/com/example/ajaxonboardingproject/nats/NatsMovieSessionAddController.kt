package com.example.ajaxonboardingproject.nats

import com.example.ajaxonboardingproject.MovieSessionOuterClass.MovieSessionResponse
import com.example.ajaxonboardingproject.MovieSessionOuterClass.MovieSessionRequest
import com.example.ajaxonboardingproject.NatsSubject
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

    override val subject: String = NatsSubject.ADD_NEW_MOVIE_SESSION_SUBJECT

    override val parser: Parser<MovieSessionRequest> =
        MovieSessionRequest.parser()

    override fun generateReplyForNatsRequest(
        request: MovieSessionRequest
    ): MovieSessionResponse {
        val movieSession: MovieSession =
            service.add(converter.protoRequestToMovieSession(request)).block()!!
        return converter.movieSessionToProtoResponse(movieSession)
    }
}
