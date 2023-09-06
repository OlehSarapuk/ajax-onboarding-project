package com.example.ajaxonboardingproject.controller

import com.example.ajaxonboardingproject.model.CinemaHall
import com.example.ajaxonboardingproject.service.CinemaHallService
import com.example.ajaxonboardingproject.service.proto.converter.CinemaHallConverter
import io.nats.client.Connection
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component
import java.io.ByteArrayOutputStream
import java.io.ObjectOutputStream

@Component
class NatsCinemaHallAddController(
    private val cinemaHallService: CinemaHallService,
    private val cinemaHallConverter: CinemaHallConverter,
    private val natsConnection: Connection
) {
    @PostConstruct
    fun listenToNatsSubject() {
        val subject = "cinemaHall.add"
        natsConnection.subscribe(subject)
        val dispatcher = natsConnection.createDispatcher { message ->
            val receivedMessage = CinemaHallOuterClass.CinemaHall.parseFrom(message.data)
            val response = add(receivedMessage)
            natsConnection.publish(message.replyTo, response.toByteArray())
        }
        dispatcher.subscribe(subject)
    }

    fun add(
        requestProto: CinemaHallOuterClass.CinemaHall
    ): CinemaHallOuterClass.CinemaHall {
        val cinemaHall: CinemaHall = cinemaHallService.add(cinemaHallConverter.protoToCinemaHall(requestProto))
        return cinemaHallConverter.cinemaHallToProto(cinemaHall)
    }
}

@Component
class NatsCinemaHallGetAllController(
    private val cinemaHallService: CinemaHallService,
    private val cinemaHallConverter: CinemaHallConverter,
    private val natsConnection: Connection
) {
    @PostConstruct
    fun listenToNatsSubject() {
        val subject = "cinemaHall.getAll"
        natsConnection.subscribe(subject)
        val dispatcher = natsConnection.createDispatcher { message ->
            val byteArrayOutputStream = ByteArrayOutputStream()
            val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
            getAllCinemaHalls().map { objectOutputStream.writeObject(it) }
            val bytes = byteArrayOutputStream.toByteArray()
            natsConnection.publish(message.replyTo, bytes)
        }
        dispatcher.subscribe(subject)
    }

    fun getAllCinemaHalls(): List<CinemaHallOuterClass.CinemaHall> {
        return cinemaHallService.getAll()
            .map { cinemaHallConverter.cinemaHallToProto(it) }
            .toList()
    }
}
