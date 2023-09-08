package com.example.ajaxonboardingproject.controller

import ListOfMoviesOuterClass
import MovieOuterClass
import com.example.ajaxonboardingproject.model.Movie
import com.example.ajaxonboardingproject.service.MovieService
import com.example.ajaxonboardingproject.service.proto.converter.MovieConverter
import com.google.protobuf.Parser
import io.nats.client.Connection
import io.nats.client.Message
import org.springframework.stereotype.Component

@Component
class NatsMovieAddController(
    private val service: MovieService,
    private val converter: MovieConverter,
    override val connection: Connection
) : NatsController<MovieOuterClass.Movie> {
    override val subject: String = "movie.add"
    override val parser: Parser<MovieOuterClass.Movie> =
        MovieOuterClass.Movie.parser()

    fun generateReplyForNatsRequest(
        message: Message
    ): ByteArray {
        val requestProto = parser.parseFrom(message.data)
        val movie: Movie = service.add(converter.protoToMovie(requestProto))
        return converter.movieToProto(movie).toByteArray()
    }
}

@Component
class NatsMovieGetAllController(
    private val converter: MovieConverter,
    private val service: MovieService,
    override val connection: Connection
) : NatsController<MovieOuterClass.Movie> {
    override val subject: String = "movie.getAll"
    override val parser: Parser<MovieOuterClass.Movie> =
        MovieOuterClass.Movie.parser()

    fun generateReplyForNatsRequest(
        message: Message
    ): ByteArray {
        val listOfProto = service.getAll()
            .map { converter.movieToProto(it) }
            .toList()
        return ListOfMoviesOuterClass.ListOfMovies
            .newBuilder()
            .addAllMovies(listOfProto)
            .build()
            .toByteArray()
    }
}
