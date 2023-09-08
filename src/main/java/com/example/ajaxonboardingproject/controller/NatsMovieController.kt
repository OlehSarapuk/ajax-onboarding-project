package com.example.ajaxonboardingproject.controller

import MovieOuterClass
import com.example.ajaxonboardingproject.model.Movie
import com.example.ajaxonboardingproject.service.MovieService
import com.example.ajaxonboardingproject.service.proto.converter.MovieConverter
import io.nats.client.Connection
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component

@Component
class NatsMovieAddController(
    private val connection: Connection,
    private val converter: MovieConverter,
    private val service: MovieService
) {
    @PostConstruct
    fun listenToNatsSubject() {
        val subject = "movie.add"
        connection.subscribe(subject)
        val dispatcher = connection.createDispatcher { message ->
            val receivedMessage = MovieOuterClass.Movie.parseFrom(message.data)
            val response = add(receivedMessage)
            connection.publish(message.replyTo, response.toByteArray())
        }
        dispatcher.subscribe(subject)
    }

    fun add(
        requestProto: MovieOuterClass.Movie
    ): MovieOuterClass.Movie {
        val movie: Movie = service.add(converter.protoToMovie(requestProto))
        return converter.movieToProto(movie)
    }
}

@Component
class NatsMovieGetAllController(
    private val connection: Connection,
    private val converter: MovieConverter,
    private val service: MovieService
) {
    @PostConstruct
    fun listenToNatsSubject() {
        val subject = "movie.getAll"
        connection.subscribe(subject)
        val dispatcher = connection.createDispatcher { message ->
            val response = getAllMovies()
            connection.publish(message.replyTo, response.toByteArray())
        }
        dispatcher.subscribe(subject)
    }

    fun getAllMovies(): ListOfMoviesOuterClass.ListOfMovies {
         val listOfProto = service.getAll()
             .map { converter.movieToProto(it) }
             .toList()
        return ListOfMoviesOuterClass.ListOfMovies
            .newBuilder()
            .addAllMovies(listOfProto)
            .build()
    }
}
