package com.example.ajaxonboardingproject

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.example.ajaxonboardingproject.model.Movie
import com.example.ajaxonboardingproject.repository.MovieRepository
import com.example.ajaxonboardingproject.service.proto.converter.MovieConverter
import io.nats.client.Connection
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
        //Given
        val movie = Movie(title = "proto TITLE", description = "grate one")
        val expected = MovieOuterClass.MovieRequest.newBuilder()
            .setMovie(movieConverter.movieToProto(movie))
            .build()
        //When
        val future = natsConnection.requestWithTimeout(
            NatsSubject.ADD_NEW_MOVIE_SUBJECT,
            expected.toByteArray(),
            Duration.ofMillis(100000))
        //Then
        val actual = MovieOuterClass.MovieResponse.parseFrom(future.get().data)
        assertThat(expected.movie).isEqualTo(actual.movie)
    }

    @Test
    fun getAllMoviesTestOk() {
        //Given
        val protoFromDb = movieRepository.findAll()
            .map { movieConverter.movieToProto(it) }
            .collectList()
            .block()
        val expected = ListOfMoviesOuterClass.ListOfMovies
            .newBuilder()
            .addAllMovies(protoFromDb)
            .build()
        //When
        val future = natsConnection.requestWithTimeout(
            NatsSubject.FIND_ALL_MOVIES_SUBJECT,
            null,
            Duration.ofMillis(100000))
        //Then
        val actual = ListOfMoviesOuterClass.ListOfMovies.parseFrom(future.get().data)
        assertThat(expected).isEqualTo(actual)
    }
}
