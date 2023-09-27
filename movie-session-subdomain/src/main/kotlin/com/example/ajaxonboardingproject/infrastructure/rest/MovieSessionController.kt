package com.example.ajaxonboardingproject.infrastructure.rest

import com.example.ajaxonboardingproject.dto.RequestDtoMapper
import com.example.ajaxonboardingproject.dto.ResponseDtoMapper
import com.example.ajaxonboardingproject.util.DATE_PATTERN
import com.example.ajaxonboardingproject.domain.MovieSession
import com.example.ajaxonboardingproject.application.dto.MovieSessionRequestDto
import com.example.ajaxonboardingproject.application.dto.MovieSessionResponseDto
import com.example.ajaxonboardingproject.application.service.MovieSessionInPort
import com.mongodb.client.result.DeleteResult
import jakarta.validation.Valid
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@RestController
@RequestMapping("/movie-sessions")
data class MovieSessionController(
    private val movieSessionInPort: MovieSessionInPort,
    private val movieSessionRequestDtoMapper: RequestDtoMapper<MovieSessionRequestDto, Mono<MovieSession>>,
    private val movieSessionResponseDtoMapper: ResponseDtoMapper<MovieSessionResponseDto, MovieSession>
) {
    @PostMapping
    fun add(
        @Valid @RequestBody requestDto: MovieSessionRequestDto
    ): Mono<MovieSessionResponseDto> {
        return movieSessionRequestDtoMapper.mapToModel(requestDto)
            .flatMap { movieSessionInPort.add(it) }
            .map { movieSessionResponseDtoMapper.mapToDto(it) }
    }

    @GetMapping("/available")
    fun findAvailableSessions(
        @RequestParam movieId: String,
        @RequestParam @DateTimeFormat(pattern = DATE_PATTERN) date: LocalDateTime
    ): Flux<MovieSessionResponseDto> {
        return movieSessionInPort.findAvailableSessions(movieId, date)
            .map(movieSessionResponseDtoMapper::mapToDto)
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: String,
        @Valid @RequestBody requestDto: MovieSessionRequestDto
    ): Mono<MovieSessionResponseDto> {
        return movieSessionRequestDtoMapper.mapToModel(requestDto)
            .map { it.copy(id = id ) }
            .flatMap { movieSessionInPort.update(it) }
            .map { movieSessionResponseDtoMapper.mapToDto(it) }
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: String
    ): Mono<DeleteResult> {
        return movieSessionInPort.delete(id)
    }
}
