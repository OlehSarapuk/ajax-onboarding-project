package com.example.ajaxonboardingproject.nats

import com.example.ajaxonboardingproject.MovieSessionOuterClass
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
) : NatsController<MovieSessionOuterClass.MovieSessionDeleteRequest, MovieSessionOuterClass.MovieSessionResponse> {

    override val subject: String = NatsSubject.DELETE_MOVIE_SESSION_SUBJECT

    override val parser: Parser<MovieSessionOuterClass.MovieSessionDeleteRequest> =
        MovieSessionOuterClass.MovieSessionDeleteRequest.parser()

    override fun generateReplyForNatsRequest(
        request: MovieSessionOuterClass.MovieSessionDeleteRequest
    ): Mono<MovieSessionOuterClass.MovieSessionResponse> {
        service.delete(request.id).block()
        return Mono.just(MovieSessionOuterClass.MovieSessionResponse.newBuilder().build())
    }
}
