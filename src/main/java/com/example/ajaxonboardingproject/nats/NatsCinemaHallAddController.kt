package com.example.ajaxonboardingproject.nats

import com.example.ajaxonboardingproject.CinemaHallOuterClass.CinemaHallResponse
import com.example.ajaxonboardingproject.CinemaHallOuterClass.CinemaHallRequest
import com.example.ajaxonboardingproject.NatsSubject
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
    override val subject: String = NatsSubject.ADD_NEW_CINEMA_HALL_SUBJECT
    override val parser: Parser<CinemaHallRequest> =
        CinemaHallRequest.parser()

    override fun generateReplyForNatsRequest(
        request: CinemaHallRequest
    ): CinemaHallResponse {
        val cinemaHall: CinemaHall = service.add(converter.protoRequestToCinemaHall(request))
        return converter.cinemaHallToProtoResponse(cinemaHall)
    }
}
