package com.example.ajaxonboardingproject.nats

import com.example.ajaxonboardingproject.MovieSessionDeleteRequest
import com.example.ajaxonboardingproject.MovieSessionResponse
import com.example.ajaxonboardingproject.NatsSubject
import com.example.ajaxonboardingproject.service.MovieSessionService
import com.google.protobuf.Parser
import io.nats.client.Connection
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class NatsMovieSessionDeleteController(
    private val service: MovieSessionService,
    override val connection: Connection
) : NatsController<MovieSessionDeleteRequest, MovieSessionResponse> {

    override val subject: String = NatsSubject.DELETE_MOVIE_SESSION_SUBJECT

    override val parser: Parser<MovieSessionDeleteRequest> =
        MovieSessionDeleteRequest.parser()

    override fun generateReplyForNatsRequest(
        request: MovieSessionDeleteRequest
    ): Mono<MovieSessionResponse> {
        service.delete(request.id).block()
        return Mono.just(MovieSessionResponse.newBuilder().build())
    }
}
