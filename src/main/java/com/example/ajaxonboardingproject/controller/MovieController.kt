package com.example.ajaxonboardingproject.controller

import com.example.ajaxonboardingproject.dto.request.MovieRequestDto
import com.example.ajaxonboardingproject.dto.response.MovieResponseDto
import com.example.ajaxonboardingproject.model.Movie
import com.example.ajaxonboardingproject.service.MovieService
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
@RequestMapping("/movies")
data class MovieController(
    private val movieService: MovieService,
    private val movieRequestDtoMapper: RequestDtoMapper<MovieRequestDto, Movie>,
    private val movieResponseDtoMapper: ResponseDtoMapper<MovieResponseDto, Movie>
) {
    @PostMapping
    fun add(
        @Valid @RequestBody requestDto: MovieRequestDto
    ): Mono<MovieResponseDto> {
        return movieService.add(movieRequestDtoMapper.mapToModel(requestDto))
            .map { movieResponseDtoMapper.mapToDto(it) }
    }

    @GetMapping
    fun getAll(): Flux<MovieResponseDto> {
        return movieService.getAll()
            .map(movieResponseDtoMapper::mapToDto)
    }
}
