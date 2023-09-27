package com.example.ajaxonboardingproject.infrastructure.rest

import com.example.ajaxonboardingproject.dto.RequestDtoMapper
import com.example.ajaxonboardingproject.dto.ResponseDtoMapper
import com.example.ajaxonboardingproject.application.dto.MovieRequestDto
import com.example.ajaxonboardingproject.application.dto.MovieResponseDto
import com.example.ajaxonboardingproject.application.service.MovieInPort
import com.example.ajaxonboardingproject.domain.Movie
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/movies")
data class MovieController(
    private val movieInPort: MovieInPort,
    private val movieRequestDtoMapper: RequestDtoMapper<MovieRequestDto, Movie>,
    private val movieResponseDtoMapper: ResponseDtoMapper<MovieResponseDto, Movie>
) {
    @PostMapping
    fun add(
        @Valid @RequestBody requestDto: MovieRequestDto
    ): Mono<MovieResponseDto> {
        return movieInPort.add(movieRequestDtoMapper.mapToModel(requestDto))
            .map { movieResponseDtoMapper.mapToDto(it) }
    }

    @GetMapping
    fun getAll(): Flux<MovieResponseDto> {
        return movieInPort.getAll()
            .map(movieResponseDtoMapper::mapToDto)
    }
}
