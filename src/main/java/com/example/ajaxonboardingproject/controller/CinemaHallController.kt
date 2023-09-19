package com.example.ajaxonboardingproject.controller

import com.example.ajaxonboardingproject.dto.request.CinemaHallRequestDto
import com.example.ajaxonboardingproject.dto.response.CinemaHallResponseDto
import com.example.ajaxonboardingproject.kafka.CinemaHallObserver
import com.example.ajaxonboardingproject.model.CinemaHall
import com.example.ajaxonboardingproject.service.CinemaHallService
import com.example.ajaxonboardingproject.service.mapper.RequestDtoMapper
import com.example.ajaxonboardingproject.service.mapper.ResponseDtoMapper
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/cinema-halls")
data class CinemaHallController(
    private val cinemaHallService: CinemaHallService,
    private val cinemaHallRequestDtoMapper: RequestDtoMapper<CinemaHallRequestDto, CinemaHall>,
    private val cinemaHallResponseDtoMapper: ResponseDtoMapper<CinemaHallResponseDto, CinemaHall>
) {
    @PostMapping
    fun add(
        @Valid @RequestBody requestDto: CinemaHallRequestDto
    ): Mono<CinemaHallResponseDto> {
        return cinemaHallService.add(cinemaHallRequestDtoMapper.mapToModel(requestDto))
            .map{ cinemaHallResponseDtoMapper.mapToDto(it) }
    }

    @GetMapping
    fun getAll(): Flux<CinemaHallResponseDto> {
        CinemaHallObserver().observe()
        return cinemaHallService.getAll()
            .map(cinemaHallResponseDtoMapper::mapToDto)
    }
}
