package com.example.ajaxonboardingproject.controller

import CinemaHallOuterClass.CinemaHallResponse
import CinemaHallOuterClass.CinemaHallRequest
import ListOfCinemaHallsOuterClass.ListOfCinemaHalls
import com.example.ajaxonboardingproject.model.CinemaHall
import com.example.ajaxonboardingproject.service.CinemaHallService
import com.example.ajaxonboardingproject.service.proto.converter.CinemaHallConverter
import com.google.protobuf.Parser
import io.nats.client.Connection
import org.springframework.stereotype.Component

@Component
class NatsCinemaHallAddController(
    private val converter: CinemaHallConverter,
    private val service: CinemaHallService,
    override val connection: Connection
) : NatsController<CinemaHallRequest, CinemaHallResponse> {
    override val subject: String = "cinemaHall.add"
    override val parser: Parser<CinemaHallRequest> =
        CinemaHallRequest.parser()

    override fun generateReplyForNatsRequest(
        request: CinemaHallRequest
    ): CinemaHallResponse {
        val cinemaHall: CinemaHall = service.add(converter.protoRequestToCinemaHall(request))
        return converter.cinemaHallToProtoResponse(cinemaHall)
    }
}

@Component
class NatsCinemaHallGetAllController(
    private val converter: CinemaHallConverter,
    private val service: CinemaHallService,
    override val connection: Connection
) : NatsController<CinemaHallRequest, ListOfCinemaHalls> {
    override val subject: String = "cinemaHall.getAll"
    override val parser: Parser<CinemaHallRequest> =
        CinemaHallRequest.parser()

    @Suppress("UnusedParameter")
    override fun generateReplyForNatsRequest(
        request: CinemaHallRequest
    ): ListOfCinemaHalls {
        val cinemaHalls = service.getAll()
            .map { converter.cinemaHallToProto(it) }
            .toList()
        return ListOfCinemaHalls
            .newBuilder()
            .addAllCinemaHalls(cinemaHalls)
            .build()
    }
}
