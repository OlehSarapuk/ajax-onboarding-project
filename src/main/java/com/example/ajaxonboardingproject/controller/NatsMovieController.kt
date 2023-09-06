package com.example.ajaxonboardingproject.controller

import MovieOuterClass
import com.example.ajaxonboardingproject.model.Movie
import com.example.ajaxonboardingproject.service.MovieService
import com.example.ajaxonboardingproject.service.proto.converter.MovieConverter
import io.nats.client.Connection
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component
import java.io.ByteArrayOutputStream
import java.io.ObjectOutputStream

@Component
class NatsMovieAddController(
    private val movieService: MovieService,
    private val movieConverter: MovieConverter,
    private val natsConnection: Connection
) {
    @PostConstruct
    fun listenToNatsSubject() {
        val subject = "movie.add"
        natsConnection.subscribe(subject)
        val dispatcher = natsConnection.createDispatcher { message ->
            val receivedMessage = MovieOuterClass.Movie.parseFrom(message.data)
            val response = add(receivedMessage)
            natsConnection.publish(message.replyTo, response.toByteArray())
        }
        dispatcher.subscribe(subject)
    }

    fun add(
        requestProto: MovieOuterClass.Movie
    ): MovieOuterClass.Movie {
        val movie: Movie = movieService.add(movieConverter.protoToMovie(requestProto))
        return movieConverter.movieToProto(movie)
    }
}

@Component
class NatsMovieGetAllController(
    private val movieService: MovieService,
    private val movieConverter: MovieConverter,
    private val natsConnection: Connection
) {
    @PostConstruct
    fun listenToNatsSubject() {
        val subject = "movie.getAll"
        natsConnection.subscribe(subject)
        val dispatcher = natsConnection.createDispatcher { message ->
            val byteArrayOutputStream = ByteArrayOutputStream()
            val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
            getAllMovies().map { objectOutputStream.writeObject(it) }
            val bytes = byteArrayOutputStream.toByteArray()
            natsConnection.publish(message.replyTo, bytes)
        }
        dispatcher.subscribe(subject)
    }

    fun getAllMovies(): List<MovieOuterClass.Movie> {
         return movieService.getAll()
             .map { movieConverter.movieToProto(it) }
             .toList()
    }
}
