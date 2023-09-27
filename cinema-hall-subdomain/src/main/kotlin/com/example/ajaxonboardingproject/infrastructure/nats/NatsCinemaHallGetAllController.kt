package com.example.ajaxonboardingproject.infrastructure.nats

import com.example.ajaxonboardingproject.CinemaHallRequest
import com.example.ajaxonboardingproject.ListOfCinemaHalls
import com.example.ajaxonboardingproject.NatsSubject
import com.example.ajaxonboardingproject.application.proto.converter.CinemaHallConverter
import com.example.ajaxonboardingproject.application.service.CinemaHallInPort
import com.example.ajaxonboardingproject.nats.NatsController
import com.google.protobuf.Parser
import io.nats.client.Connection
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class NatsCinemaHallGetAllController(
    private val converter: CinemaHallConverter,
    private val service: CinemaHallInPort,
    override val connection: Connection
) : NatsController<CinemaHallRequest, ListOfCinemaHalls> {

    override val subject: String = NatsSubject.FIND_ALL_CINEMA_HALLS_SUBJECT

    override val parser: Parser<CinemaHallRequest> =
        CinemaHallRequest.parser()

    @Suppress("UnusedParameter")
    override fun generateReplyForNatsRequest(
        request: CinemaHallRequest
    ): Mono<ListOfCinemaHalls> {
        return service.getAll()
            .map { converter.cinemaHallToProto(it) }
            .collectList()
            .map { ListOfCinemaHalls.newBuilder().addAllCinemaHalls(it).build() }
    }
}
