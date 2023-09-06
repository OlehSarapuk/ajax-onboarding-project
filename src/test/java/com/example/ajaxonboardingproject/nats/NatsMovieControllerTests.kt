package com.example.ajaxonboardingproject.nats

import com.example.ajaxonboardingproject.model.Movie
import com.example.ajaxonboardingproject.repository.MovieRepository
import com.example.ajaxonboardingproject.service.proto.converter.MovieConverter
import io.nats.client.Connection
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.io.ByteArrayInputStream
import java.io.EOFException
import java.io.ObjectInputStream
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
        val proto = movieConverter.movieToProto(movie)
        val future = natsConnection.requestWithTimeout("movie.add", proto.toByteArray(), Duration.ofMillis(100000))
        val reply = MovieOuterClass.Movie.parseFrom(future.get().data)
        assert(proto == reply)
    }

    @Test
    @Suppress("EmptyCatchBlock", "SwallowedException")
    fun getAllMoviesTestOk() {
        val protos = movieRepository.findAll().
        map { movieConverter.movieToProto(it) }
        val future = natsConnection.requestWithTimeout("movie.getAll", "".toByteArray(), Duration.ofMillis(100000))
        val objectInputStream = ObjectInputStream( ByteArrayInputStream(future.get().data))
        val result = mutableListOf<Any>()
        try {
            while (true) {
                result.add(objectInputStream.readObject());
            }
        } catch (e: EOFException) {}
        assert(protos == result)
    }
}
