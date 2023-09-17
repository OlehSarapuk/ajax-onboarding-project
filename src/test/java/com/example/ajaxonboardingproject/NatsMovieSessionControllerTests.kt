package com.example.ajaxonboardingproject

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isGreaterThan
import com.example.ajaxonboardingproject.model.CinemaHall
import com.example.ajaxonboardingproject.model.Movie
import com.example.ajaxonboardingproject.model.MovieSession
import com.example.ajaxonboardingproject.repository.MovieSessionRepository
import com.example.ajaxonboardingproject.service.proto.converter.MovieSessionConverter
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
        val request = MovieSessionOuterClass.MovieSessionRequest.newBuilder()
            .setMovieSession(movieSessionConverter.movieSessionToProto(movieSession))
            .build()
        //When
        val future = natsConnection.requestWithTimeout(
            NatsSubject.ADD_NEW_MOVIE_SESSION_SUBJECT,
            request.toByteArray(),
            Duration.ofMillis(100000)
        )
        //Then
        val reply = MovieSessionOuterClass.MovieSessionResponse.parseFrom(future.get().data)
        assertThat(request.movieSession).isEqualTo(reply.movieSession)
    }

    @Test
    fun updateMovieSessionTestOk() {
        //Given
        val movieSessionFromDB = movieSessionRepository.findAll().collectList().block()!!.first()
        val movie = Movie(title = "proto TITLE", description = "grate one")
        val cinemaHall = CinemaHall(capacity = 100, description = "grate one")
        val movieSession = MovieSession(movie = movie, cinemaHall = cinemaHall, showTime = LocalDateTime.now())
        val expected = MovieSessionOuterClass.MovieSessionRequest.newBuilder()
            .setMovieSession(movieSessionConverter.movieSessionToProto(movieSession))
            .build()
        //When
        val future = natsConnection.requestWithTimeout(
            NatsSubject.UPDATE_MOVIE_SESSION_SUBJECT.changeSubjectSuffixWithId(movieSessionFromDB.id),
            expected.toByteArray(),
            Duration.ofMillis(100000)
        )
        //Then
        val actual = MovieSessionOuterClass.MovieSessionResponse.parseFrom(future.get().data)
        assertThat(expected.movieSession).isEqualTo(actual.movieSession)
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
        //When
        val future = natsConnection.requestWithTimeout(
            NatsSubject.DELETE_MOVIE_SESSION_SUBJECT.changeSubjectSuffixWithId(movieSessionFromDB.id),
            null,
            Duration.ofMillis(100000)
        )
        future.get().data
        //Then
        val sizeOfDBAfter = movieSessionRepository.findAll().collectList().block()!!.size
        assertThat(sizeOfDBBefore).isGreaterThan(sizeOfDBAfter)
    }
}

fun String.changeSubjectSuffixWithId(id: String): String {
    return this.removeSuffix("*") + id
}
