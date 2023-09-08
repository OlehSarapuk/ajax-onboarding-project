package com.example.ajaxonboardingproject.controller

import CinemaHallOuterClass
import ListOfCinemaHallsOuterClass
import com.example.ajaxonboardingproject.model.CinemaHall
import com.example.ajaxonboardingproject.service.CinemaHallService
import com.example.ajaxonboardingproject.service.proto.converter.CinemaHallConverter
import com.google.protobuf.Parser
import io.nats.client.Connection
import io.nats.client.Message
import org.springframework.stereotype.Component

@Component
class NatsCinemaHallAddController(
    private val converter: CinemaHallConverter,
    private val service: CinemaHallService,
    override val connection: Connection
) : NatsController<CinemaHallOuterClass.CinemaHall> {
    override val subject: String = "cinemaHall.add"
    override val parser: Parser<CinemaHallOuterClass.CinemaHall> =
        CinemaHallOuterClass.CinemaHall.parser()

    fun generateReplyForNatsRequest(
        message: Message
    ): ByteArray {
        val requestProto = parser.parseFrom(message.data)
        val cinemaHall: CinemaHall = service.add(converter.protoToCinemaHall(requestProto))
        return converter.cinemaHallToProto(cinemaHall).toByteArray()
    }
}

@Component
class NatsCinemaHallGetAllController(
    private val converter: CinemaHallConverter,
    private val service: CinemaHallService,
    override val connection: Connection
) : NatsController<CinemaHallOuterClass.CinemaHall> {
    override val subject: String = "cinemaHall.getAll"
    override val parser: Parser<CinemaHallOuterClass.CinemaHall> =
        CinemaHallOuterClass.CinemaHall.parser()

    @Suppress("UnusedParameter")
    fun generateReplyForNatsRequest(
        message: Message
    ): ByteArray {
        val cinemaHalls = service.getAll()
            .map { converter.cinemaHallToProto(it) }
            .toList()
        return ListOfCinemaHallsOuterClass.ListOfCinemaHalls
            .newBuilder()
            .addAllCinemaHalls(cinemaHalls)
            .build()
            .toByteArray()
    }
}
