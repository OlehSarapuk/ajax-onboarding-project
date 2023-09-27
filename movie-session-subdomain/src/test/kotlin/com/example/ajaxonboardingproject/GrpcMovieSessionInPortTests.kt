package com.example.ajaxonboardingproject

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isGreaterThan
import com.example.ajaxonboardingproject.application.proto.converter.MovieSessionConverter
import com.example.ajaxonboardingproject.application.repository.MovieSessionRepositoryOutPort
import com.example.ajaxonboardingproject.domain.CinemaHall
import com.example.ajaxonboardingproject.domain.Movie
import com.example.ajaxonboardingproject.domain.MovieSession
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
class GrpcMovieSessionInPortTests(
    @Value("\${spring.grpc.port}")
    var grpcPort: Int
) {
    @Autowired
    private lateinit var movieSessionConverter: MovieSessionConverter

    @Autowired
    private lateinit var movieSessionRepositoryOutPort: MovieSessionRepositoryOutPort

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
        val movie = Movie(id = null, title = "proto TITLE", description = "grate one")
        val cinemaHall = CinemaHall(id = null, capacity = 100, description = "grate one")
        val movieSession =
            MovieSession(id = null, movie = movie, cinemaHall = cinemaHall, showTime = LocalDateTime.now())
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
        val movie = Movie(id = null, title = "proto TITLE", description = "grate one")
        val cinemaHall = CinemaHall(id = null, capacity = 100, description = "grate one")
        val movieSession =
            MovieSession(id = null, movie = movie, cinemaHall = cinemaHall, showTime = LocalDateTime.now())
        movieSessionRepositoryOutPort.save(movieSession).block()
        val cinemaHallToUpdate = CinemaHall(id = null, capacity = 10, description = "grate")
        val movieSessionToUpdate =
            MovieSession(id = null, movie = movie, cinemaHall = cinemaHallToUpdate, showTime = LocalDateTime.now())
        val movieSessionFromDB = movieSessionRepositoryOutPort.findAll().blockFirst()!!
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
        val movie = Movie(id = null, title = "proto TITLE", description = "grate one")
        val cinemaHall = CinemaHall(id = null, capacity = 100, description = "grate one")
        val movieSession =
            MovieSession(id = null, movie = movie, cinemaHall = cinemaHall, showTime = LocalDateTime.now())
        movieSessionRepositoryOutPort.save(movieSession).block()
        val sizeOfDBBefore = movieSessionRepositoryOutPort.findAll().collectList().block()!!.size
        val movieSessionFromDB = movieSessionRepositoryOutPort.findAll().blockFirst()!!
        val movieSessionRequest = MovieSessionDeleteRequest.newBuilder()
            .setId(movieSessionFromDB.id)
            .build()
        //When
        stub.deleteMovieSession(movieSessionRequest)
        //Then
        val sizeOfDBAfter = movieSessionRepositoryOutPort.findAll().collectList().block()!!.size
        assertThat(sizeOfDBBefore).isGreaterThan(sizeOfDBAfter)
    }

    @AfterEach
    fun shutDownServer() {
        channel.shutdown()
    }
}
