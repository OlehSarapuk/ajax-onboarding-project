package com.example.ajaxonboardingproject.controller

import com.example.ajaxonboardingproject.model.CinemaHall
import com.example.ajaxonboardingproject.service.CinemaHallService
import com.example.ajaxonboardingproject.service.proto.converter.CinemaHallConverter
import io.nats.client.Connection
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component

@Component
class NatsCinemaHallAddController(
    private val connection: Connection,
    private val converter: CinemaHallConverter,
    private val service: CinemaHallService
) {
    @PostConstruct
    fun listenToNatsSubject() {
        val subject = "cinemaHall.add"
        connection.subscribe(subject)
        val dispatcher = connection.createDispatcher { message ->
            val receivedMessage = CinemaHallOuterClass.CinemaHall.parseFrom(message.data)
            val response = add(receivedMessage)
            connection.publish(message.replyTo, response.toByteArray())
        }
        dispatcher.subscribe(subject)
    }

    fun add(
        requestProto: CinemaHallOuterClass.CinemaHall
    ): CinemaHallOuterClass.CinemaHall {
        val cinemaHall: CinemaHall = service.add(converter.protoToCinemaHall(requestProto))
        return converter.cinemaHallToProto(cinemaHall)
    }
}

@Component
class NatsCinemaHallGetAllController(
    private val connection: Connection,
    private val converter: CinemaHallConverter,
    private val service: CinemaHallService
) {
    @PostConstruct
    fun listenToNatsSubject() {
        val subject = "cinemaHall.getAll"
        connection.subscribe(subject)
        val dispatcher = connection.createDispatcher { message ->
            val response = getAllCinemaHalls()
            connection.publish(message.replyTo, response.toByteArray())
        }
        dispatcher.subscribe(subject)
    }

    fun getAllCinemaHalls(): ListOfCinemaHallsOuterClass.ListOfCinemaHalls {
        val cinemaHalls = service.getAll()
            .map { converter.cinemaHallToProto(it) }
            .toList()
        return ListOfCinemaHallsOuterClass.ListOfCinemaHalls
            .newBuilder()
            .addAllCinemaHalls(cinemaHalls)
            .build()
    }
}
