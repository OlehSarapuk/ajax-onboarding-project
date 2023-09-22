package com.example.ajaxonboardingproject.cinemahall.rest

import com.example.ajaxonboardingproject.cinemahall.service.grpc.observer.CinemaHallObserver
import com.example.ajaxonboardingproject.cinemahall.dto.CinemaHallRequestDto
import com.example.ajaxonboardingproject.cinemahall.dto.CinemaHallResponseDto
import com.example.ajaxonboardingproject.cinemahall.service.CinemaHallService
import com.example.ajaxonboardingproject.cinemahall.model.CinemaHall
import com.example.ajaxonboardingproject.infrastructure.dto.RequestDtoMapper
import com.example.ajaxonboardingproject.infrastructure.dto.ResponseDtoMapper
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
    private val cinemaHallResponseDtoMapper: ResponseDtoMapper<CinemaHallResponseDto, CinemaHall>,
    private val cinemaHallObserver: CinemaHallObserver
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
        cinemaHallObserver.observe()
        return cinemaHallService.getAll()
            .map(cinemaHallResponseDtoMapper::mapToDto)
    }
}
