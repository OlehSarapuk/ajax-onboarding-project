package com.example.ajaxonboardingproject.service.proto.converter

import com.example.ajaxonboardingproject.CinemaHallProto
import com.example.ajaxonboardingproject.CinemaHallRequest
import com.example.ajaxonboardingproject.CinemaHallResponse
import com.example.ajaxonboardingproject.model.CinemaHall
import org.springframework.stereotype.Component

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
            capacity = cinemaHallProto.cinemaHall.capacity,
            description = cinemaHallProto.cinemaHall.description
        )
    }
}
