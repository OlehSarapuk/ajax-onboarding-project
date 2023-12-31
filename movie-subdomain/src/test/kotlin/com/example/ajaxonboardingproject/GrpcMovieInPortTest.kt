package com.example.ajaxonboardingproject

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.example.ajaxonboardingproject.application.proto.converter.MovieConverter
import com.example.ajaxonboardingproject.application.service.MovieInPort
import com.example.ajaxonboardingproject.domain.Movie
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class GrpcMovieInPortTest(
    @Value("\${spring.grpc.port}")
    var grpcPort: Int
) {
    @Autowired
    private lateinit var movieInPort: MovieInPort

    @Autowired
    private lateinit var movieConverter: MovieConverter

    private lateinit var stub: MovieServiceGrpc.MovieServiceBlockingStub

    private lateinit var channel: ManagedChannel

    @BeforeEach
    fun startServer() {
        channel = ManagedChannelBuilder
            .forAddress("localhost", grpcPort)
            .usePlaintext()
            .build()
        stub = MovieServiceGrpc.newBlockingStub(channel)
    }

    @Test
    fun addMovieGrpcTestOk() {
        //Given
        val movie = Movie(id = null, title = "Django Unchained", description = "grate one")
        val expected = MovieRequest.newBuilder()
            .setMovie(movieConverter.movieToProto(movie))
            .build()
        //When
        val actual: MovieResponse = stub.addMovie(expected)
        //Then
        assertThat(actual.movie).isEqualTo(expected.movie)
    }

    @Test
    fun getAllCinemaHallsTestOk() {
        //Given
        val expected = movieInPort.getAll().collectList().block()!!
        //When
        val allMovies = stub.getAllMovies(MovieRequest.newBuilder().build())
        //Then
        val actual: MutableList<Movie> = mutableListOf()
        allMovies
            .forEach { actual.add(movieConverter.protoToMovie(it.movie)) }
        assertThat(actual.size).isEqualTo(expected.size)
    }

    @AfterEach
    fun shutDownServer() {
        channel.shutdown()
    }
}
