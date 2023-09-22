package com.example.ajaxonboardingproject.cinemahall.service.mapper

import com.example.ajaxonboardingproject.cinemahall.dto.CinemaHallRequestDto
import com.example.ajaxonboardingproject.cinemahall.dto.CinemaHallResponseDto
import com.example.ajaxonboardingproject.cinemahall.model.CinemaHall
import com.example.ajaxonboardingproject.infrastructure.dto.RequestDtoMapper
import com.example.ajaxonboardingproject.infrastructure.dto.ResponseDtoMapper
import org.springframework.stereotype.Component

@Component
class CinemaHallMapper : RequestDtoMapper<CinemaHallRequestDto, CinemaHall>,
    ResponseDtoMapper<CinemaHallResponseDto, CinemaHall> {
    override fun mapToModel(dto: CinemaHallRequestDto): CinemaHall {
        return CinemaHall(
            description = dto.description,
            capacity = dto.capacity
        )
    }

    override fun mapToDto(model: CinemaHall): CinemaHallResponseDto {
        return CinemaHallResponseDto(
            id = model.id,
            capacity = model.capacity,
            description = model.description
        )
    }
}
