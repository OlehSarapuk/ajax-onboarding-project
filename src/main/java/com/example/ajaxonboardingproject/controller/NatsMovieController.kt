package com.example.ajaxonboardingproject.controller

import ListOfMoviesOuterClass.ListOfMovies
import MovieOuterClass.MovieResponse
import MovieOuterClass.MovieRequest
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
    override val subject: String = "movie.add"
    override val parser: Parser<MovieRequest> =
        MovieRequest.parser()

    override fun generateReplyForNatsRequest(
        request: MovieRequest
    ): MovieResponse {
        val movie: Movie = service.add(converter.protoRequestToMovie(request))
        return converter.movieToProtoResponse(movie)
    }
}

@Component
class NatsMovieGetAllController(
    private val converter: MovieConverter,
    private val service: MovieService,
    override val connection: Connection
) : NatsController<MovieRequest, ListOfMovies> {
    override val subject: String = "movie.getAll"
    override val parser: Parser<MovieRequest> =
        MovieRequest.parser()

    @Suppress("UnusedParameter")
    override fun generateReplyForNatsRequest(
        request: MovieRequest
    ): ListOfMovies {
        val listOfProto = service.getAll()
            .map { converter.movieToProto(it) }
            .toList()
        return ListOfMovies
            .newBuilder()
            .addAllMovies(listOfProto)
            .build()
    }
}
