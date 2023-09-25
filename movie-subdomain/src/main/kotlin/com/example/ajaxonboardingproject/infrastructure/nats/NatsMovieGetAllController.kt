package com.example.ajaxonboardingproject.infrastructure.nats

import com.example.ajaxonboardingproject.ListOfMovies
import com.example.ajaxonboardingproject.MovieRequest
import com.example.ajaxonboardingproject.NatsSubject
import com.example.ajaxonboardingproject.nats.NatsController
import com.example.ajaxonboardingproject.application.proto.converter.MovieConverter
import com.example.ajaxonboardingproject.application.service.MovieService
import com.google.protobuf.Parser
import io.nats.client.Connection
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class NatsMovieGetAllController(
    private val converter: MovieConverter,
    private val service: MovieService,
    override val connection: Connection
) : NatsController<MovieRequest, ListOfMovies> {

    override val subject: String = NatsSubject.FIND_ALL_MOVIES_SUBJECT

    override val parser: Parser<MovieRequest> =
        MovieRequest.parser()

    @Suppress("UnusedParameter")
    override fun generateReplyForNatsRequest(
        request: MovieRequest
    ): Mono<ListOfMovies> {
        return service.getAll()
            .map { converter.movieToProto(it) }
            .collectList()
            .map { ListOfMovies.newBuilder().addAllMovies(it).build() }
    }
}
