package com.example.ajaxonboardingproject.infrastructure.rest

import com.example.ajaxonboardingproject.infrastructure.grpc.observer.CinemaHallObserver
import com.example.ajaxonboardingproject.application.dto.CinemaHallRequestDto
import com.example.ajaxonboardingproject.application.dto.CinemaHallResponseDto
import com.example.ajaxonboardingproject.application.service.CinemaHallInPort
import com.example.ajaxonboardingproject.domain.CinemaHall
import com.example.ajaxonboardingproject.dto.RequestDtoMapper
import com.example.ajaxonboardingproject.dto.ResponseDtoMapper
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
    private val cinemaHallInPort: CinemaHallInPort,
    private val cinemaHallRequestDtoMapper: RequestDtoMapper<CinemaHallRequestDto, CinemaHall>,
    private val cinemaHallResponseDtoMapper: ResponseDtoMapper<CinemaHallResponseDto, CinemaHall>,
    private val cinemaHallObserver: CinemaHallObserver
) {
    @PostMapping
    fun add(
        @Valid @RequestBody requestDto: CinemaHallRequestDto
    ): Mono<CinemaHallResponseDto> {
        return cinemaHallInPort.add(cinemaHallRequestDtoMapper.mapToModel(requestDto))
            .map{ cinemaHallResponseDtoMapper.mapToDto(it) }
    }

    @GetMapping
    fun getAll(): Flux<CinemaHallResponseDto> {
        cinemaHallObserver.observe()
        return cinemaHallInPort.getAll()
            .map(cinemaHallResponseDtoMapper::mapToDto)
    }
}
