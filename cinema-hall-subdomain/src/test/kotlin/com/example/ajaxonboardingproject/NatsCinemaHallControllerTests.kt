package com.example.ajaxonboardingproject

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.example.ajaxonboardingproject.application.repository.CinemaHallRepositoryOutPort
import com.example.ajaxonboardingproject.domain.CinemaHall
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
    lateinit var cinemaHallConverter: com.example.ajaxonboardingproject.application.proto.converter.CinemaHallConverter

    @Autowired
    lateinit var cinemaHallRepositoryOutPort: CinemaHallRepositoryOutPort

    @Test
    fun addCinemaHallTestOk() {
        //Given
        val cinemaHall = CinemaHall(
            id = null,
            capacity = 100,
            description = "grate one"
        )
        val expected =
            CinemaHallRequest.newBuilder().setCinemaHall(cinemaHallConverter.cinemaHallToProto(cinemaHall)).build()
        //When
        val future = natsConnection
            .requestWithTimeout(
                NatsSubject.ADD_NEW_CINEMA_HALL_SUBJECT,
                expected.toByteArray(),
                Duration.ofMillis(100000)
            )
        //Then
        val actual = CinemaHallResponse.parseFrom(future.get().data)
        assertThat(actual.cinemaHall).isEqualTo(expected.cinemaHall)
    }

    @Test
    fun getAllCinemaHallsTestOk() {
        //Given
        val protoFromDb = cinemaHallRepositoryOutPort.findAll()
            .map { cinemaHallConverter.cinemaHallToProto(it) }
            .collectList()
            .block()
        val expected = ListOfCinemaHalls.newBuilder().addAllCinemaHalls(protoFromDb).build()
        //When
        val future = natsConnection.requestWithTimeout(
            NatsSubject.FIND_ALL_CINEMA_HALLS_SUBJECT,
            null,
            Duration.ofMillis(100000)
        )
        //Then
        val actual = ListOfCinemaHalls.parseFrom(future.get().data)
        assertThat(actual).isEqualTo(expected)
    }
}
