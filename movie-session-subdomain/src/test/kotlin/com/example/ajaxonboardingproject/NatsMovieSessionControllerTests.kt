package com.example.ajaxonboardingproject

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isGreaterThan
import com.example.ajaxonboardingproject.domain.CinemaHall
import com.example.ajaxonboardingproject.domain.MovieSession
import com.example.ajaxonboardingproject.domain.Movie
import com.example.ajaxonboardingproject.application.repository.MovieSessionRepository
import com.example.ajaxonboardingproject.application.proto.converter.MovieSessionConverter
import io.nats.client.Connection
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.Duration
import java.time.LocalDateTime

@SpringBootTest
class NatsMovieSessionControllerTests {
    @Autowired
    lateinit var natsConnection: Connection

    @Autowired
    lateinit var movieSessionConverter: MovieSessionConverter

    @Autowired
    lateinit var movieSessionRepository: MovieSessionRepository

    @Test
    fun addMovieSessionTestOk() {
        //Given
        val movie = Movie(title = "proto TITLE", description = "grate one")
        val cinemaHall = CinemaHall(capacity = 100, description = "grate one")
        val movieSession = MovieSession(movie = movie, cinemaHall = cinemaHall, showTime = LocalDateTime.now())
        val expected = MovieSessionAddRequest.newBuilder()
            .setMovieSession(movieSessionConverter.movieSessionToProto(movieSession))
            .build()
        //When
        val future = natsConnection.requestWithTimeout(
            NatsSubject.ADD_NEW_MOVIE_SESSION_SUBJECT,
            expected.toByteArray(),
            Duration.ofMillis(100000)
        )
        //Then
        val actual = MovieSessionResponse.parseFrom(future.get().data)
        assertThat(actual.movieSession).isEqualTo(expected.movieSession)
    }

    @Test
    fun updateMovieSessionTestOk() {
        //Given
        val movieSessionFromDB = movieSessionRepository.findAll().collectList().block()!!.first()
        val movie = Movie(title = "Nats", description = "grate one")
        val cinemaHall = CinemaHall(capacity = 100, description = "grate one")
        val movieSession = MovieSession(movie = movie, cinemaHall = cinemaHall, showTime = LocalDateTime.now())
        val expected =
            MovieSessionUpdateRequest.newBuilder()
                .setId(movieSessionFromDB.id)
                .setMovieSession(movieSessionConverter.movieSessionToProto(movieSession))
                .build()
        //When
        val future = natsConnection.requestWithTimeout(
            NatsSubject.UPDATE_MOVIE_SESSION_SUBJECT,
            expected.toByteArray(),
            Duration.ofMillis(100000)
        )
        //Then
        val actual = MovieSessionResponse.parseFrom(future.get().data)
        assertThat(actual.movieSession).isEqualTo(expected.movieSession)
    }

    @Test
    fun deleteMovieSessionTestOk() {
        //Given
        val movie = Movie(title = "proto TITLE", description = "grate one")
        val cinemaHall = CinemaHall(capacity = 100, description = "grate one")
        val movieSession = MovieSession(movie = movie, cinemaHall = cinemaHall, showTime = LocalDateTime.now())
        movieSessionRepository.save(movieSession).block()
        val sizeOfDBBefore = movieSessionRepository.findAll().collectList().block()!!.size
        val movieSessionFromDB = movieSessionRepository.findAll().collectList().block()!!.first()
        val movieSessionRequest =
            MovieSessionUpdateRequest.newBuilder().setId(movieSessionFromDB.id).build()
        //When
        val future = natsConnection.requestWithTimeout(
            NatsSubject.DELETE_MOVIE_SESSION_SUBJECT,
            movieSessionRequest.toByteArray(),
            Duration.ofMillis(100000)
        )
        future.get().data
        //Then
        val sizeOfDBAfter = movieSessionRepository.findAll().collectList().block()!!.size
        assertThat(sizeOfDBBefore).isGreaterThan(sizeOfDBAfter)
    }
}