package com.example.ajaxonboardingproject.nats

import com.example.ajaxonboardingproject.ListOfMovies
import com.example.ajaxonboardingproject.MovieRequest
import com.example.ajaxonboardingproject.NatsSubject
import com.example.ajaxonboardingproject.service.MovieService
import com.example.ajaxonboardingproject.service.proto.converter.MovieConverter
import com.google.protobuf.Parser
import io.nats.client.Connection
import org.springframework.stereotype.Component

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
    ): ListOfMovies {
        val listOfProto = service.getAll()
            .map { converter.movieToProto(it) }
            .collectList()
            .block()!!
            .toList()
        return ListOfMovies
            .newBuilder()
            .addAllMovies(listOfProto)
            .build()
    }
}
