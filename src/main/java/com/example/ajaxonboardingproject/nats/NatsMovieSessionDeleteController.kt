package com.example.ajaxonboardingproject.nats

import com.example.ajaxonboardingproject.MovieSessionOuterClass
import com.example.ajaxonboardingproject.NatsSubject
import com.example.ajaxonboardingproject.service.MovieSessionService
import com.google.protobuf.Parser
import io.nats.client.Connection
import org.springframework.stereotype.Component

@Component
class NatsMovieSessionDeleteController(
    private val service: MovieSessionService,
    override val connection: Connection
) : NatsController<MovieSessionOuterClass.MovieSessionRequest, MovieSessionOuterClass.MovieSessionResponse> {
    override val subject: String = NatsSubject.DELETE_MOVIE_SESSION_SUBJECT
    override val parser: Parser<MovieSessionOuterClass.MovieSessionRequest> =
        MovieSessionOuterClass.MovieSessionRequest.parser()

    override fun generateReplyForNatsRequest(
        request: MovieSessionOuterClass.MovieSessionRequest
    ): MovieSessionOuterClass.MovieSessionResponse {
        service.delete(subject.split(".").last())
        return MovieSessionOuterClass.MovieSessionResponse.newBuilder().build()
    }
}
