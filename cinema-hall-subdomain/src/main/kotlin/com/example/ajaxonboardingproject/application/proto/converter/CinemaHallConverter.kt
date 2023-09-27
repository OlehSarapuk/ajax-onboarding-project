package com.example.ajaxonboardingproject.application.proto.converter

import com.example.ajaxonboardingproject.CinemaHallRequest
import com.example.ajaxonboardingproject.CinemaHallResponse
import com.example.ajaxonboardingproject.domain.CinemaHall
import org.springframework.stereotype.Component
import com.example.ajaxonboardingproject.CinemaHall as CinemaHallProto

@Component
class CinemaHallConverter {
    fun cinemaHallToProto(
        cinemaHall: CinemaHall
    ): CinemaHallProto {
        return CinemaHallProto.newBuilder()
            .setCapacity(cinemaHall.capacity)
            .setDescription(cinemaHall.description)
            .build()
    }

    fun protoToCinemaHall(
        cinemaHallProto: CinemaHallProto
    ): CinemaHall {
        return CinemaHall(
            id = null,
            capacity = cinemaHallProto.capacity,
            description = cinemaHallProto.description
        )
    }

    fun cinemaHallToProtoResponse(
        cinemaHall: CinemaHall
    ): CinemaHallResponse {
        return CinemaHallResponse.newBuilder()
            .setCinemaHall(cinemaHallToProto(cinemaHall))
            .build()
    }

    fun protoRequestToCinemaHall(
        cinemaHallProto: CinemaHallRequest
    ): CinemaHall {
        return CinemaHall(
            id = null,
            capacity = cinemaHallProto.cinemaHall.capacity,
            description = cinemaHallProto.cinemaHall.description
        )
    }
}
