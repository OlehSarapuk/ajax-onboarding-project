package com.example.ajaxonboardingproject.nats

import com.example.ajaxonboardingproject.ListOfMoviesOuterClass
import com.example.ajaxonboardingproject.MovieOuterClass
import com.example.ajaxonboardingproject.NatsSubject
import com.example.ajaxonboardingproject.service.MovieService
import com.example.ajaxonboardingproject.service.proto.converter.MovieConverter
import com.google.protobuf.Parser
import io.nats.client.Connection
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class NatsMovieGetAllController(
    private val converter: MovieConverter,
    private val service: MovieService,
    override val connection: Connection
) : NatsController<MovieOuterClass.MovieRequest, ListOfMoviesOuterClass.ListOfMovies> {

    override val subject: String = NatsSubject.FIND_ALL_MOVIES_SUBJECT

    override val parser: Parser<MovieOuterClass.MovieRequest> =
        MovieOuterClass.MovieRequest.parser()

    @Suppress("UnusedParameter")
    override fun generateReplyForNatsRequest(
        request: MovieOuterClass.MovieRequest
    ): Mono<ListOfMoviesOuterClass.ListOfMovies> {
        return service.getAll()
            .map { converter.movieToProto(it) }
            .collectList()
            .map { ListOfMoviesOuterClass.ListOfMovies
                .newBuilder()
                .addAllMovies(it)
                .build() }
    }
}
