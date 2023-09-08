package com.example.ajaxonboardingproject.service.proto.converter

import CinemaHallOuterClass
import com.example.ajaxonboardingproject.model.CinemaHall
import org.springframework.stereotype.Component

@Component
class CinemaHallConverter {
    fun cinemaHallToProto(
        cinemaHall: CinemaHall
    ): CinemaHallOuterClass.CinemaHall {
        return CinemaHallOuterClass.CinemaHall.newBuilder()
            .setCapacity(cinemaHall.capacity)
            .setDescription(cinemaHall.description)
            .build()
    }

    fun protoToCinemaHall(
        cinemaHallProto: CinemaHallOuterClass.CinemaHall
    ): CinemaHall {
        return CinemaHall(
            capacity = cinemaHallProto.capacity,
            description = cinemaHallProto.description
        )
    }
}
