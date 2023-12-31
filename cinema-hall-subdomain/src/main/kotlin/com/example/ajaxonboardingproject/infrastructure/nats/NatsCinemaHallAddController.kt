package com.example.ajaxonboardingproject.infrastructure.nats

import com.example.ajaxonboardingproject.CinemaHallRequest
import com.example.ajaxonboardingproject.CinemaHallResponse
import com.example.ajaxonboardingproject.NatsSubject
import com.example.ajaxonboardingproject.application.proto.converter.CinemaHallConverter
import com.example.ajaxonboardingproject.application.service.CinemaHallInPort
import com.example.ajaxonboardingproject.nats.NatsController
import com.google.protobuf.Parser
import io.nats.client.Connection
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class NatsCinemaHallAddController(
    private val converter: CinemaHallConverter,
    private val service: CinemaHallInPort,
    override val connection: Connection
) : NatsController<CinemaHallRequest, CinemaHallResponse> {

    override val subject: String = NatsSubject.ADD_NEW_CINEMA_HALL_SUBJECT

    override val parser: Parser<CinemaHallRequest> =
        CinemaHallRequest.parser()

    override fun generateReplyForNatsRequest(
        request: CinemaHallRequest
    ): Mono<CinemaHallResponse> {
        return service.add(converter.protoRequestToCinemaHall(request))
            .map { converter.cinemaHallToProtoResponse(it) }
    }
}
