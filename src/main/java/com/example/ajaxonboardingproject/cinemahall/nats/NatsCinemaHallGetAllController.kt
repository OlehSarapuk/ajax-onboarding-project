package com.example.ajaxonboardingproject.cinemahall.nats

import com.example.ajaxonboardingproject.CinemaHallRequest
import com.example.ajaxonboardingproject.ListOfCinemaHalls
import com.example.ajaxonboardingproject.NatsSubject
import com.example.ajaxonboardingproject.cinemahall.service.CinemaHallService
import com.example.ajaxonboardingproject.cinemahall.service.proto.converter.CinemaHallConverter
import com.example.ajaxonboardingproject.infrastructure.nats.NatsController
import com.google.protobuf.Parser
import io.nats.client.Connection
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class NatsCinemaHallGetAllController(
    private val converter: CinemaHallConverter,
    private val service: CinemaHallService,
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
