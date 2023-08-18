package com.example.ajaxonboardingproject.controller;

import com.example.ajaxonboardingproject.dto.request.CinemaHallRequestDto;
import com.example.ajaxonboardingproject.dto.response.CinemaHallResponseDto;
import com.example.ajaxonboardingproject.model.CinemaHall;
import com.example.ajaxonboardingproject.service.CinemaHallService;
import com.example.ajaxonboardingproject.service.mapper.RequestDtoMapper;
import com.example.ajaxonboardingproject.service.mapper.ResponseDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cinema-halls")
public class CinemaHallController {
    private final CinemaHallService cinemaHallService;
    private final RequestDtoMapper<CinemaHallRequestDto, CinemaHall> cinemaHallRequestDtoMapper;
    private final ResponseDtoMapper<CinemaHallResponseDto, CinemaHall> cinemaHallResponseDtoMapper;

    @PostMapping
    public CinemaHallResponseDto add(@RequestBody @Valid CinemaHallRequestDto requestDto) {
        CinemaHall cinemaHall = cinemaHallService.add(
                cinemaHallRequestDtoMapper.mapToModel(requestDto));
        return cinemaHallResponseDtoMapper.mapToDto(cinemaHall);
    }

    @GetMapping
    public List<CinemaHallResponseDto> getAll() {
        return cinemaHallService.getAll()
                .stream()
                .map(cinemaHallResponseDtoMapper::mapToDto)
                .toList();
    }
}
