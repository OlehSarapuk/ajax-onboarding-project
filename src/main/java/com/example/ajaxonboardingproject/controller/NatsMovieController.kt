package com.example.ajaxonboardingproject.controller

import MovieOuterClass
import com.example.ajaxonboardingproject.model.Movie
import com.example.ajaxonboardingproject.service.MovieService
import com.example.ajaxonboardingproject.service.proto.converter.MovieConverter
import io.nats.client.Connection
import io.nats.client.Nats
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class NatsMovieController(
    private val movieService: MovieService,
    private val movieConverter: MovieConverter,
    private val natsConnection: Connection
) {
    @PostConstruct
    fun subscribeToNatsSubject() {
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
        request: MovieOuterClass.Movie
    ): MovieOuterClass.Movie {
        val movie: Movie = movieService.add(movieConverter.protoToMovie(request))
        return movieConverter.movieToProto(movie)
    }

//    fun test() {
//        val proto = MovieOuterClass.Movie.newBuilder()
//            .setDescription("i am protobuf")
//            .setTitle("i am title of protobuf")
//            .build()
//        val future = natsConnection.requestWithTimeout("movie.add", proto.toByteArray(), Duration.ofMillis(100000))
//        val reply = future.get()
//        println(reply)
//    }
}
