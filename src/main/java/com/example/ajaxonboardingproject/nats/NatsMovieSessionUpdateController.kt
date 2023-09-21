package com.example.ajaxonboardingproject.nats

import com.example.ajaxonboardingproject.MovieSessionResponse
import com.example.ajaxonboardingproject.MovieSessionUpdateRequest
import com.example.ajaxonboardingproject.NatsSubject
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
) : NatsController<MovieSessionUpdateRequest, MovieSessionResponse> {

    override val subject: String = NatsSubject.UPDATE_MOVIE_SESSION_SUBJECT

    override val parser: Parser<MovieSessionUpdateRequest> =
        MovieSessionUpdateRequest.parser()

    override fun generateReplyForNatsRequest(
        request: Mono<MovieSessionUpdateRequest>
    ): Mono<MovieSessionResponse> {
        return request
            .map {
                request -> converter.protoToMovieSession(request.movieSession).apply {
                    id = request.id
                }
            }
            .flatMap { service.update(it) }
            .map { converter.movieSessionToProtoResponse(it) }
    }
}
