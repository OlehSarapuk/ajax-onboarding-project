package com.example.ajaxonboardingproject.nats

import com.example.ajaxonboardingproject.MovieSessionAddRequest
import com.example.ajaxonboardingproject.MovieSessionResponse
import com.example.ajaxonboardingproject.NatsSubject
import com.example.ajaxonboardingproject.service.MovieSessionService
import com.example.ajaxonboardingproject.service.proto.converter.MovieSessionConverter
import com.google.protobuf.Parser
import io.nats.client.Connection
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class NatsMovieSessionAddController(
    private val service: MovieSessionService,
    private val converter: MovieSessionConverter,
    override val connection: Connection
) : NatsController<MovieSessionAddRequest, MovieSessionResponse> {

    override val subject: String = NatsSubject.ADD_NEW_MOVIE_SESSION_SUBJECT

    override val parser: Parser<MovieSessionAddRequest> =
        MovieSessionAddRequest.parser()

    override fun generateReplyForNatsRequest(
        request: MovieSessionAddRequest
    ): Mono<MovieSessionResponse> {
        return service.add(converter.protoToMovieSession(request.movieSession))
            .map { converter.movieSessionToProtoResponse(it) }
    }
}
