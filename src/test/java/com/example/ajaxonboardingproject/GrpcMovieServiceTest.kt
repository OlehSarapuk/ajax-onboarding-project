package com.example.ajaxonboardingproject

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.example.ajaxonboardingproject.model.Movie
import com.example.ajaxonboardingproject.service.MovieService
import com.example.ajaxonboardingproject.service.proto.converter.MovieConverter
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class GrpcMovieServiceTest {
    @Autowired
    private lateinit var movieService: MovieService

    @Autowired
    private lateinit var movieConverter: MovieConverter

    private lateinit var stub: MovieServiceGrpc.MovieServiceBlockingStub

    private lateinit var channel: ManagedChannel

    @BeforeEach
    fun startServer() {
        channel = ManagedChannelBuilder
            .forAddress("localhost", 8097)
            .usePlaintext()
            .build()
        stub = MovieServiceGrpc.newBlockingStub(channel)
    }

    @Test
    fun addMovieGrpcTestOk() {
        //Given
        val movie = Movie(title = "Django Unchained", description = "grate one")
        val expected = MovieRequest.newBuilder()
            .setMovie(movieConverter.movieToProto(movie))
            .build()
        //When
        val actual: MovieResponse = stub.addMovie(expected)
        //Then
        assertThat(expected.movie).isEqualTo(actual.movie)
    }

    @Test
    fun getAllCinemaHallsTestOk() {
        //Given
        val expected = movieService.getAll().collectList().block()!!
        //When
        val allMovies = stub.getAllMovies(MovieRequest.newBuilder().build())
        //Then
        val actual: MutableList<Movie> = mutableListOf()
        allMovies
            .forEach { actual.add(movieConverter.protoToMovie(it.movie)) }
        assertThat(expected.size).isEqualTo(actual.size)
    }

    @AfterEach
    fun shutDownServer() {
        channel.shutdown()
    }
}
