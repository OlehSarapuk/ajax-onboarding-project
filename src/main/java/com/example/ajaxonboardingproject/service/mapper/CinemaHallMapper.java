package com.example.ajaxonboardingproject.service.mapper;

import com.example.ajaxonboardingproject.dto.request.CinemaHallRequestDto;
import com.example.ajaxonboardingproject.dto.response.CinemaHallResponseDto;
import com.example.ajaxonboardingproject.model.CinemaHall;
import org.springframework.stereotype.Component;

@Component
public class CinemaHallMapper implements RequestDtoMapper<CinemaHallRequestDto, CinemaHall>,
        ResponseDtoMapper<CinemaHallResponseDto, CinemaHall> {
    @Override
    public CinemaHall mapToModel(CinemaHallRequestDto dto) {
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setDescription(dto.getDescription());
        cinemaHall.setCapacity(dto.getCapacity());
        return cinemaHall;
    }

    @Override
    public CinemaHallResponseDto mapToDto(CinemaHall cinemaHall) {
        return new CinemaHallResponseDto(
                cinemaHall.getId(),
                cinemaHall.getCapacity(),
                cinemaHall.getDescription());
    }
}
