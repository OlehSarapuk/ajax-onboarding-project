package com.example.ajaxonboardingproject

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.example.ajaxonboardingproject.model.CinemaHall
import com.example.ajaxonboardingproject.repository.CinemaHallRepository
import com.example.ajaxonboardingproject.service.proto.converter.CinemaHallConverter
import io.nats.client.Connection
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
        //Given
        val cinemaHall = CinemaHall(capacity = 100, description = "grate one")
        val expected = CinemaHallOuterClass.CinemaHallRequest.newBuilder()
            .setCinemaHall(cinemaHallConverter.cinemaHallToProto(cinemaHall))
            .build()
        //When
        val future = natsConnection
            .requestWithTimeout(
                NatsSubject.ADD_NEW_CINEMA_HALL_SUBJECT,
                expected.toByteArray(),
                Duration.ofMillis(100000))
        //Then
        val actual = CinemaHallOuterClass.CinemaHallResponse.parseFrom(future.get().data)
        assertThat(expected.cinemaHall).isEqualTo(actual.cinemaHall)
    }

    @Test
    fun getAllCinemaHallsTestOk() {
        //Given
        val protoFromDb = cinemaHallRepository.findAll()
            .map { cinemaHallConverter.cinemaHallToProto(it) }
            .collectList()
            .block()
        val expected = ListOfCinemaHallsOuterClass.ListOfCinemaHalls
            .newBuilder()
            .addAllCinemaHalls(protoFromDb)
            .build()
        //When
        val future = natsConnection.requestWithTimeout(
            NatsSubject.FIND_ALL_CINEMA_HALLS_SUBJECT,
            null,
            Duration.ofMillis(100000))
        //Then
        val actual = ListOfCinemaHallsOuterClass.ListOfCinemaHalls.parseFrom(future.get().data)
        assertThat(expected).isEqualTo(actual)
    }
}
