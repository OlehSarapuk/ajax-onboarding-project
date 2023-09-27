package com.example.ajaxonboardingproject.application.mapper

import com.example.ajaxonboardingproject.application.dto.CinemaHallRequestDto
import com.example.ajaxonboardingproject.application.dto.CinemaHallResponseDto
import com.example.ajaxonboardingproject.domain.CinemaHall
import com.example.ajaxonboardingproject.dto.RequestDtoMapper
import com.example.ajaxonboardingproject.dto.ResponseDtoMapper
import org.springframework.stereotype.Component

@Component
class CinemaHallMapper : RequestDtoMapper<CinemaHallRequestDto, CinemaHall>,
    ResponseDtoMapper<CinemaHallResponseDto, CinemaHall> {
    override fun mapToModel(dto: CinemaHallRequestDto): CinemaHall {
        return CinemaHall(
            id = null,
            description = dto.description,
            capacity = dto.capacity
        )
    }

    override fun mapToDto(model: CinemaHall): CinemaHallResponseDto {
        return CinemaHallResponseDto(
            id = model.id ?: throw NoSuchElementException("movie has no id"),
            capacity = model.capacity,
            description = model.description
        )
    }
}
