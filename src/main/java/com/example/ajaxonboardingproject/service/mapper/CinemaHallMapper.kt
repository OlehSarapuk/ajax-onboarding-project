package com.example.ajaxonboardingproject.service.mapper

import com.example.ajaxonboardingproject.dto.request.CinemaHallRequestDto
import com.example.ajaxonboardingproject.dto.response.CinemaHallResponseDto
import com.example.ajaxonboardingproject.model.CinemaHall
import org.springframework.stereotype.Component

@Component
class CinemaHallMapper : RequestDtoMapper<CinemaHallRequestDto, CinemaHall>,
        ResponseDtoMapper<CinemaHallResponseDto, CinemaHall> {
    override fun mapToModel(dto : CinemaHallRequestDto) : CinemaHall {
        return CinemaHall(
                description = dto.description,
                capacity = dto.capacity
        )
    }

    override fun mapToDto(model: CinemaHall): CinemaHallResponseDto {
        return model.id?.let { CinemaHallResponseDto(
                id = it,
                capacity = model.capacity,
                description = model.description)}!!
    }
}