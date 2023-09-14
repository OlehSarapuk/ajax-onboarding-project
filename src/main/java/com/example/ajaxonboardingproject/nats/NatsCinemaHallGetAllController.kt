package com.example.ajaxonboardingproject.nats

import com.example.ajaxonboardingproject.CinemaHallOuterClass
import com.example.ajaxonboardingproject.ListOfCinemaHallsOuterClass
import com.example.ajaxonboardingproject.NatsSubject
import com.example.ajaxonboardingproject.service.CinemaHallService
import com.example.ajaxonboardingproject.service.proto.converter.CinemaHallConverter
import com.google.protobuf.Parser
import io.nats.client.Connection
import org.springframework.stereotype.Component

@Component
class NatsCinemaHallGetAllController(
    private val converter: CinemaHallConverter,
    private val service: CinemaHallService,
    override val connection: Connection
) : NatsController<CinemaHallOuterClass.CinemaHallRequest, ListOfCinemaHallsOuterClass.ListOfCinemaHalls> {

    override val subject: String = NatsSubject.FIND_ALL_CINEMA_HALLS_SUBJECT

    override val parser: Parser<CinemaHallOuterClass.CinemaHallRequest> =
        CinemaHallOuterClass.CinemaHallRequest.parser()

    @Suppress("UnusedParameter")
    override fun generateReplyForNatsRequest(
        request: CinemaHallOuterClass.CinemaHallRequest
    ): ListOfCinemaHallsOuterClass.ListOfCinemaHalls {
        val cinemaHalls = service.getAll()
            .map { converter.cinemaHallToProto(it) }
            .toList()
        return ListOfCinemaHallsOuterClass.ListOfCinemaHalls
            .newBuilder()
            .addAllCinemaHalls(cinemaHalls)
            .build()
    }
}
