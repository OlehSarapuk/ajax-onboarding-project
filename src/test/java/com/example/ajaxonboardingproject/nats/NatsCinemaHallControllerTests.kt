package com.example.ajaxonboardingproject.nats

import com.example.ajaxonboardingproject.model.CinemaHall
import com.example.ajaxonboardingproject.repository.CinemaHallRepository
import com.example.ajaxonboardingproject.service.proto.converter.CinemaHallConverter
import io.nats.client.Connection
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
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
        Assertions.assertEquals(proto, reply)
    }

    @Test
    fun getAllCinemaHallsTestOk() {
        val protos = cinemaHallRepository.findAll()
            .map { cinemaHallConverter.cinemaHallToProto(it) }
        val expected = ListOfCinemaHallsOuterClass.ListOfCinemaHalls
            .newBuilder()
            .addAllCinemaHalls(protos)
            .build()
        val future = natsConnection.requestWithTimeout("cinemaHall.getAll", "".toByteArray(), Duration.ofMillis(100000))
        val result = ListOfCinemaHallsOuterClass.ListOfCinemaHalls.parseFrom(future.get().data)
        Assertions.assertEquals(expected, result)
    }
}
