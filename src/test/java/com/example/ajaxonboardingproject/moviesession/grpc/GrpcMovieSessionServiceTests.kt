package com.example.ajaxonboardingproject.moviesession.grpc

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isGreaterThan
import com.example.ajaxonboardingproject.MovieSessionAddRequest
import com.example.ajaxonboardingproject.MovieSessionDeleteRequest
import com.example.ajaxonboardingproject.MovieSessionServiceGrpc
import com.example.ajaxonboardingproject.MovieSessionUpdateRequest
import com.example.ajaxonboardingproject.cinemahall.model.CinemaHall
import com.example.ajaxonboardingproject.movie.model.Movie
import com.example.ajaxonboardingproject.moviesession.model.MovieSession
import com.example.ajaxonboardingproject.moviesession.repsitory.MovieSessionRepository
import com.example.ajaxonboardingproject.moviesession.service.proto.converter.MovieSessionConverter
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class GrpcMovieSessionServiceTests(
    @Value("\${spring.grpc.port}")
    var grpcPort: Int
) {
    @Autowired
    private lateinit var movieSessionConverter: MovieSessionConverter

    @Autowired
    private lateinit var movieSessionRepository: MovieSessionRepository

    private lateinit var stub: MovieSessionServiceGrpc.MovieSessionServiceBlockingStub

    private lateinit var channel: ManagedChannel

    @BeforeEach
    fun startServer() {
        channel = ManagedChannelBuilder
            .forAddress("localhost", grpcPort)
            .usePlaintext()
            .build()
        stub = MovieSessionServiceGrpc.newBlockingStub(channel)
    }

    @Test
    fun addMovieSessionGrpcTestOk() {
        //Given
        val movie = Movie(title = "proto TITLE", description = "grate one")
        val cinemaHall = CinemaHall(capacity = 100, description = "grate one")
        val movieSession = MovieSession(movie = movie, cinemaHall = cinemaHall, showTime = LocalDateTime.now())
        val expected = MovieSessionAddRequest.newBuilder()
            .setMovieSession(movieSessionConverter.movieSessionToProto(movieSession))
            .build()
        //When
        val actual = stub.addMovieSession(expected)
        //Then
        assertThat(actual.movieSession).isEqualTo(expected.movieSession)
    }

    @Test
    fun updateMovieSessionGrpcTestOk() {
        //Given
        val movie = Movie(title = "proto TITLE", description = "grate one")
        val cinemaHall = CinemaHall(capacity = 100, description = "grate one")
        val movieSession = MovieSession(movie = movie, cinemaHall = cinemaHall, showTime = LocalDateTime.now())
        movieSessionRepository.save(movieSession).block()
        val cinemaHallToUpdate = CinemaHall(capacity = 10, description = "grate")
        val movieSessionToUpdate =
            MovieSession(movie = movie, cinemaHall = cinemaHallToUpdate, showTime = LocalDateTime.now())
        val movieSessionFromDB = movieSessionRepository.findAll().blockFirst()!!
        val expected = MovieSessionUpdateRequest.newBuilder()
            .setId(movieSessionFromDB.id)
            .setMovieSession(movieSessionConverter.movieSessionToProto(movieSessionToUpdate))
            .build()
        //When
        val actual = stub.updateMovieSession(expected)
        //Then
        assertThat(actual.movieSession).isEqualTo(expected.movieSession)
    }

    @Test
    fun deleteMovieSessionGrpcTestOk() {
        //Given
        val movie = Movie(title = "proto TITLE", description = "grate one")
        val cinemaHall = CinemaHall(capacity = 100, description = "grate one")
        val movieSession = MovieSession(movie = movie, cinemaHall = cinemaHall, showTime = LocalDateTime.now())
        movieSessionRepository.save(movieSession).block()
        val sizeOfDBBefore = movieSessionRepository.findAll().collectList().block()!!.size
        val movieSessionFromDB = movieSessionRepository.findAll().blockFirst()!!
        val movieSessionRequest = MovieSessionDeleteRequest.newBuilder()
            .setId(movieSessionFromDB.id)
            .build()
        //When
        stub.deleteMovieSession(movieSessionRequest)
        //Then
        val sizeOfDBAfter = movieSessionRepository.findAll().collectList().block()!!.size
        assertThat(sizeOfDBBefore).isGreaterThan(sizeOfDBAfter)
    }

    @AfterEach
    fun shutDownServer() {
        channel.shutdown()
    }
}
