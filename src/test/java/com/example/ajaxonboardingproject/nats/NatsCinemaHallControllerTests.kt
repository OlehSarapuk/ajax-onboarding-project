package com.example.ajaxonboardingproject.nats

import com.example.ajaxonboardingproject.model.CinemaHall
import com.example.ajaxonboardingproject.repository.CinemaHallRepository
import com.example.ajaxonboardingproject.service.proto.converter.CinemaHallConverter
import io.nats.client.Connection
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.io.ByteArrayInputStream
import java.io.EOFException
import java.io.ObjectInputStream
import java.time.Duration

@SpringBootTest
class NatsCinemaHallControllerTests {
    @Autowired
    lateinit var natsConnection: Connection
    @Autowired
    lateinit var cinemaHallConverter: CinemaHallConverter
    @Autowired
    lateinit var cinemaHallRepository: CinemaHallRepository

    @Test
    fun addCinemaHallTestOk() {
        val cinemaHall = CinemaHall(capacity = 100, description = "grate one")
        val proto = cinemaHallConverter.cinemaHallToProto(cinemaHall)
        val future = natsConnection.requestWithTimeout("cinemaHall.add", proto.toByteArray(), Duration.ofMillis(100000))
        val reply = CinemaHallOuterClass.CinemaHall.parseFrom(future.get().data)
        assert(proto == reply)
    }

    @Test
    @Suppress("EmptyCatchBlock", "SwallowedException")
    fun getAllCinemaHallsTestOk() {
        val protos = cinemaHallRepository.findAll().
        map { cinemaHallConverter.cinemaHallToProto(it) }
        val future = natsConnection.requestWithTimeout("cinemaHall.getAll", "".toByteArray(), Duration.ofMillis(100000))
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
