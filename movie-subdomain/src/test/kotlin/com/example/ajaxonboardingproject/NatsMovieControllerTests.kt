package com.example.ajaxonboardingproject

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.example.ajaxonboardingproject.application.proto.converter.MovieConverter
import com.example.ajaxonboardingproject.application.repository.MovieRepositoryOutPort
import com.example.ajaxonboardingproject.domain.Movie
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
    lateinit var movieRepositoryOutPort: MovieRepositoryOutPort

    @Test
    fun addMovieTestOk() {
        //Given
        val movie = Movie(id = null, title = "proto TITLE", description = "grate one")
        val expected = MovieRequest.newBuilder().setMovie(movieConverter.movieToProto(movie)).build()
        //When
        val future = natsConnection.requestWithTimeout(
            NatsSubject.ADD_NEW_MOVIE_SUBJECT,
            expected.toByteArray(),
            Duration.ofMillis(100000)
        )
        //Then
        val actual = MovieResponse.parseFrom(future.get().data)
        assertThat(actual.movie).isEqualTo(expected.movie)
    }

    @Test
    fun getAllMoviesTestOk() {
        //Given
        val protoFromDb = movieRepositoryOutPort.findAll()
            .map { movieConverter.movieToProto(it) }
            .collectList()
            .block()
        val expected = ListOfMovies.newBuilder().addAllMovies(protoFromDb).build()
        //When
        val future = natsConnection.requestWithTimeout(
            NatsSubject.FIND_ALL_MOVIES_SUBJECT,
            null,
            Duration.ofMillis(100000)
        )
        //Then
        val actual = ListOfMovies.parseFrom(future.get().data)
        assertThat(actual).isEqualTo(expected)
    }
}
