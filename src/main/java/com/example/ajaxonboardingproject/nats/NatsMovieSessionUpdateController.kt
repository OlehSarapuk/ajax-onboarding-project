package com.example.ajaxonboardingproject.nats

import com.example.ajaxonboardingproject.MovieSessionOuterClass
import com.example.ajaxonboardingproject.NatsSubject
import com.example.ajaxonboardingproject.model.MovieSession
import com.example.ajaxonboardingproject.service.MovieSessionService
import com.example.ajaxonboardingproject.service.proto.converter.MovieSessionConverter
import com.google.protobuf.Parser
import io.nats.client.Connection
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class NatsMovieSessionUpdateController(
    private val service: MovieSessionService,
    private val converter: MovieSessionConverter,
    override val connection: Connection
) : NatsController<MovieSessionOuterClass.MovieSessionUpdateRequest, MovieSessionOuterClass.MovieSessionResponse> {

    override val subject: String = NatsSubject.UPDATE_MOVIE_SESSION_SUBJECT

    override val parser: Parser<MovieSessionOuterClass.MovieSessionUpdateRequest> =
        MovieSessionOuterClass.MovieSessionUpdateRequest.parser()

    override fun generateReplyForNatsRequest(
        request: MovieSessionOuterClass.MovieSessionUpdateRequest
    ): Mono<MovieSessionOuterClass.MovieSessionResponse> {
        val movieSession: MovieSession = converter.protoToMovieSession(request.movieSession).apply {
            id = request.id
        }
        return service.update(movieSession)
            .map { converter.movieSessionToProtoResponse(it) }
    }
}
