package com.example.ajaxonboardingproject.nats

import com.example.ajaxonboardingproject.CinemaHallRequest
import com.example.ajaxonboardingproject.ListOfCinemaHalls
import com.example.ajaxonboardingproject.NatsSubject
import com.example.ajaxonboardingproject.service.CinemaHallService
import com.example.ajaxonboardingproject.service.proto.converter.CinemaHallConverter
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
        request: Mono<CinemaHallRequest>
    ): Mono<ListOfCinemaHalls> {
        return service.getAll()
            .map { converter.cinemaHallToProto(it) }
            .collectList()
            .map { ListOfCinemaHalls.newBuilder().addAllCinemaHalls(it).build() }
    }
}
