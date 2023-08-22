package com.example.ajaxonboardingproject.controller

import com.example.ajaxonboardingproject.dto.request.CinemaHallRequestDto
import com.example.ajaxonboardingproject.dto.response.CinemaHallResponseDto
import com.example.ajaxonboardingproject.service.CinemaHallService
import com.example.ajaxonboardingproject.service.mapper.CinemaHallMapper
import com.example.ajaxonboardingproject.service.mapper.mapToDto
import com.example.ajaxonboardingproject.service.mapper.mapToModel
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/cinema-halls")
data class CinemaHallController(
        private val cinemaHallService: CinemaHallService,
        private val cinemaHallMapper: CinemaHallMapper) {
    @PostMapping
    fun add(@Valid @RequestBody requestDto : CinemaHallRequestDto) : CinemaHallResponseDto {
        val cinemaHall = cinemaHallService.add(cinemaHallMapper.mapToModel(requestDto))
        return cinemaHallMapper.mapToDto(cinemaHall)
    }

    @GetMapping
    fun getAll() : List<CinemaHallResponseDto> {
        return cinemaHallService.getAll()
                .map (cinemaHallMapper::mapToDto)
                .toList()
    }
}
