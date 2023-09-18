package com.example.ajaxonboardingproject.nats

import com.example.ajaxonboardingproject.MovieRequest
import com.example.ajaxonboardingproject.MovieResponse
import com.example.ajaxonboardingproject.NatsSubject
import com.example.ajaxonboardingproject.model.Movie
import com.example.ajaxonboardingproject.service.MovieService
import com.example.ajaxonboardingproject.service.proto.converter.MovieConverter
import com.google.protobuf.Parser
import io.nats.client.Connection
import org.springframework.stereotype.Component

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
    ): MovieResponse {
        val movie: Movie = service.add(converter.protoRequestToMovie(request)).block()!!
        return converter.movieToProtoResponse(movie)
    }
}
