package com.example.ajaxonboardingproject

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.example.ajaxonboardingproject.model.CinemaHall
import com.example.ajaxonboardingproject.service.CinemaHallService
import com.example.ajaxonboardingproject.service.proto.converter.CinemaHallConverter
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class GrpcCinemaHallServiceTests(
    @Value("\${spring.grpc.port}")
    var grpcPort: Int
) {
    @Autowired
    private lateinit var cinemaHallService: CinemaHallService

    @Autowired
    private lateinit var cinemaHallConverter: CinemaHallConverter

    private lateinit var stub: CinemaHallServiceGrpc.CinemaHallServiceBlockingStub

    private lateinit var channel: ManagedChannel

    @BeforeEach
    fun startServer() {
        channel = ManagedChannelBuilder
            .forAddress("localhost", grpcPort)
            .usePlaintext()
            .build()
        stub = CinemaHallServiceGrpc.newBlockingStub(channel)
    }

    @Test
    fun addCinemaHallGrpcTestOk() {
        //Given
        val cinemaHall = CinemaHall(capacity = 100, description = "grate one")
        val expected = CinemaHallRequest.newBuilder()
            .setCinemaHall(cinemaHallConverter.cinemaHallToProto(cinemaHall))
            .build()
        //When
        val actual: CinemaHallResponse = stub.addCinemaHall(expected)
        //Then
        assertThat(expected.cinemaHall).isEqualTo(actual.cinemaHall)
    }

    @Test
    fun getAllCinemaHallsTestOk() {
        //Given
        val expected = cinemaHallService.getAll().collectList().block()!!
        //When
        val allCinemaHalls = stub.getAllCinemaHalls(CinemaHallRequest.newBuilder().build())
        //Then
        val actual: MutableList<CinemaHall> = mutableListOf()
        allCinemaHalls
            .forEach { actual.add(cinemaHallConverter.protoToCinemaHall(it.cinemaHall)) }
        assertThat(expected.size).isEqualTo(actual.size)
    }

    @AfterEach
    fun shutDownServer() {
        channel.shutdown()
    }
}
