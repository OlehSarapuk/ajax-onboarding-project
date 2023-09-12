package com.example.ajaxonboardingproject

import com.example.ajaxonboardingproject.model.CinemaHall
import com.example.ajaxonboardingproject.model.Movie
import com.example.ajaxonboardingproject.model.MovieSession
import com.example.ajaxonboardingproject.repository.MovieSessionRepository
import com.example.ajaxonboardingproject.service.proto.converter.MovieSessionConverter
import io.nats.client.Connection
import org.junit.jupiter.api.Assertions
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
        val movie = Movie(title = "proto TITLE", description = "grate one")
        val cinemaHall = CinemaHall(capacity = 100, description = "grate one")
        val movieSession = MovieSession(movie = movie, cinemaHall = cinemaHall, showTime = LocalDateTime.now())
        val request = MovieSessionOuterClass.MovieSessionRequest.newBuilder()
            .setMovieSession(movieSessionConverter.movieSessionToProto(movieSession))
            .build()
        val future = natsConnection.requestWithTimeout(
            "movieSession.add",
            request.toByteArray(),
            Duration.ofMillis(100000)
        )
        val reply = MovieSessionOuterClass.MovieSessionResponse.parseFrom(future.get().data)
        Assertions.assertEquals(request.movieSession, reply.movieSession)
    }

    @Test
    fun updateMovieSessionTestOk() {
        val movieSessionFromDB = movieSessionRepository.findAll().first()
        val movie = Movie(title = "proto TITLE", description = "grate one")
        val cinemaHall = CinemaHall(capacity = 100, description = "grate one")
        val movieSession = MovieSession(movie = movie, cinemaHall = cinemaHall, showTime = LocalDateTime.now())
        val request = MovieSessionOuterClass.MovieSessionRequest.newBuilder()
            .setMovieSession(movieSessionConverter.movieSessionToProto(movieSession))
            .build()
        val future = natsConnection.requestWithTimeout(
            "movieSession.update.${movieSessionFromDB.id}",
            request.toByteArray(),
            Duration.ofMillis(100000)
        )
        val reply = MovieSessionOuterClass.MovieSessionResponse.parseFrom(future.get().data)
        Assertions.assertEquals(request.movieSession, reply.movieSession)
    }

    @Test
    fun deleteMovieSessionTestOk() {
        val movie = Movie(title = "proto TITLE", description = "grate one")
        val cinemaHall = CinemaHall(capacity = 100, description = "grate one")
        val movieSession = MovieSession(movie = movie, cinemaHall = cinemaHall, showTime = LocalDateTime.now())
        movieSessionRepository.save(movieSession)
        val before = movieSessionRepository.findAll().size
        val movieSessionFromDB = movieSessionRepository.findAll().first()
        val future = natsConnection.requestWithTimeout(
            "movieSession.delete.${movieSessionFromDB.id}",
            null,
            Duration.ofMillis(100000))
        future.get().data
        val after = movieSessionRepository.findAll().size
        Assertions.assertEquals((before - 1), after)
    }
}
