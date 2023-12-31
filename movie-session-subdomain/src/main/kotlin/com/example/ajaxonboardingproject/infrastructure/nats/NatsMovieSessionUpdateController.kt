package com.example.ajaxonboardingproject.infrastructure.nats

import com.example.ajaxonboardingproject.MovieSessionResponse
import com.example.ajaxonboardingproject.MovieSessionUpdateRequest
import com.example.ajaxonboardingproject.NatsSubject
import com.example.ajaxonboardingproject.application.proto.converter.MovieSessionConverter
import com.example.ajaxonboardingproject.application.service.MovieSessionInPort
import com.example.ajaxonboardingproject.domain.MovieSession
import com.example.ajaxonboardingproject.nats.NatsController
import com.google.protobuf.Parser
import io.nats.client.Connection
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class NatsMovieSessionUpdateController(
    private val service: MovieSessionInPort,
    private val converter: MovieSessionConverter,
    override val connection: Connection
) : NatsController<MovieSessionUpdateRequest, MovieSessionResponse> {

    override val subject: String = NatsSubject.UPDATE_MOVIE_SESSION_SUBJECT

    override val parser: Parser<MovieSessionUpdateRequest> =
        MovieSessionUpdateRequest.parser()

    override fun generateReplyForNatsRequest(
        request: MovieSessionUpdateRequest
    ): Mono<MovieSessionResponse> {
        val movieSession: MovieSession = converter.protoToMovieSession(request.movieSession)
            .copy(id = request.id)
        return service.update(movieSession)
            .map { converter.movieSessionToProtoResponse(it) }
    }
}
