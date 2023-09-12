package com.example.ajaxonboardingproject

import ListOfMoviesOuterClass
import MovieOuterClass
import com.example.ajaxonboardingproject.model.Movie
import com.example.ajaxonboardingproject.repository.MovieRepository
import com.example.ajaxonboardingproject.service.proto.converter.MovieConverter
import io.nats.client.Connection
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.Duration

@SpringBootTest
class NatsMovieControllerTests {
    @Autowired
    lateinit var natsConnection: Connection

    @Autowired
    lateinit var movieConverter: MovieConverter

    @Autowired
    lateinit var movieRepository: MovieRepository

    @Test
    fun addMovieTestOk() {
        val movie = Movie(title = "proto TITLE", description = "grate one")
        val request = MovieOuterClass.MovieRequest.newBuilder()
            .setMovie(movieConverter.movieToProto(movie))
            .build()
        val future = natsConnection.requestWithTimeout("movie.add", request.toByteArray(), Duration.ofMillis(100000))
        val reply = MovieOuterClass.MovieResponse.parseFrom(future.get().data)
        Assertions.assertEquals(request.movie, reply.movie)
    }

    @Test
    fun getAllMoviesTestOk() {
        val protoFromDb = movieRepository.findAll()
            .map { movieConverter.movieToProto(it) }
        val expected = ListOfMoviesOuterClass.ListOfMovies
            .newBuilder()
            .addAllMovies(protoFromDb)
            .build()
        val future = natsConnection.requestWithTimeout("movie.getAll", null, Duration.ofMillis(100000))
        val result = ListOfMoviesOuterClass.ListOfMovies.parseFrom(future.get().data)
        Assertions.assertEquals(expected, result)
    }
}
