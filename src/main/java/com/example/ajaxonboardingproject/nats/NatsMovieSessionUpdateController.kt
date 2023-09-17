package com.example.ajaxonboardingproject.nats

import com.example.ajaxonboardingproject.MovieSessionOuterClass
import com.example.ajaxonboardingproject.NatsSubject
import com.example.ajaxonboardingproject.model.MovieSession
import com.example.ajaxonboardingproject.service.MovieSessionService
import com.example.ajaxonboardingproject.service.proto.converter.MovieSessionConverter
import com.google.protobuf.Parser
import io.nats.client.Connection
import org.springframework.stereotype.Component

@Component
class NatsMovieSessionUpdateController(
    private val service: MovieSessionService,
    private val converter: MovieSessionConverter,
    override val connection: Connection
) : NatsController<MovieSessionOuterClass.MovieSessionRequest, MovieSessionOuterClass.MovieSessionResponse> {

    override var subject: String = NatsSubject.UPDATE_MOVIE_SESSION_SUBJECT

    override val parser: Parser<MovieSessionOuterClass.MovieSessionRequest> =
        MovieSessionOuterClass.MovieSessionRequest.parser()

    override fun generateReplyForNatsRequest(
        request: MovieSessionOuterClass.MovieSessionRequest
    ): MovieSessionOuterClass.MovieSessionResponse {
        val movieSession: MovieSession = converter.protoRequestToMovieSession(request).apply {
            id = subject.substringAfterLast(".")
        }
        val updatedMovieSession = service.update(movieSession).block()!!
        return converter.movieSessionToProtoResponse(updatedMovieSession)
    }
}
