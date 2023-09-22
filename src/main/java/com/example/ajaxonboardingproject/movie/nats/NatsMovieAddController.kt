package com.example.ajaxonboardingproject.movie.nats

import com.example.ajaxonboardingproject.MovieRequest
import com.example.ajaxonboardingproject.MovieResponse
import com.example.ajaxonboardingproject.NatsSubject
import com.example.ajaxonboardingproject.infrastructure.nats.NatsController
import com.example.ajaxonboardingproject.movie.service.proto.converter.MovieConverter
import com.example.ajaxonboardingproject.movie.service.MovieService
import com.google.protobuf.Parser
import io.nats.client.Connection
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class NatsMovieAddController(
    private val service: MovieService,
    private val converter: MovieConverter,
    override val connection: Connection
) : NatsController<MovieRequest, MovieResponse> {

    override val subject: String = NatsSubject.ADD_NEW_MOVIE_SUBJECT

    override val parser: Parser<MovieRequest> =
        MovieRequest.parser()

    override fun generateReplyForNatsRequest(
        request: MovieRequest
    ): Mono<MovieResponse> {
        return service.add(converter.protoRequestToMovie(request))
            .map { converter.movieToProtoResponse(it) }
    }
}
