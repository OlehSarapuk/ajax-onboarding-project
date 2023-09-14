package com.example.ajaxonboardingproject.controller

import com.example.ajaxonboardingproject.dto.request.MovieSessionRequestDto
import com.example.ajaxonboardingproject.dto.response.MovieSessionResponseDto
import com.example.ajaxonboardingproject.model.MovieSession
import com.example.ajaxonboardingproject.service.MovieSessionService
import com.example.ajaxonboardingproject.service.mapper.RequestDtoMapper
import com.example.ajaxonboardingproject.service.mapper.ResponseDtoMapper
import com.example.ajaxonboardingproject.util.DATE_PATTERN
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
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@RestController
@RequestMapping("/movie-sessions")
data class MovieSessionController(
    private val movieSessionService: MovieSessionService,
    private val movieSessionRequestDtoMapper: RequestDtoMapper<MovieSessionRequestDto, MovieSession>,
    private val movieSessionResponseDtoMapper: ResponseDtoMapper<MovieSessionResponseDto, MovieSession>
) {
    @PostMapping
    fun add(
        @Valid @RequestBody requestDto: MovieSessionRequestDto
    ): MovieSessionResponseDto {
        val movieSession: MovieSession = movieSessionRequestDtoMapper.mapToModel(requestDto)
        return movieSessionService.add(movieSession)
            .map { movieSessionResponseDtoMapper.mapToDto(it) }
            .block()!!
    }

    @GetMapping("/available")
    fun findAvailableSessions(
        @RequestParam movieId: String,
        @RequestParam @DateTimeFormat(pattern = DATE_PATTERN) date: LocalDateTime
    ): List<MovieSessionResponseDto> {
        return movieSessionService.findAvailableSessions(movieId, date)
            .map(movieSessionResponseDtoMapper::mapToDto)
            .collectList()
            .block()!!
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: String,
        @Valid @RequestBody requestDto: MovieSessionRequestDto
    ): Mono<MovieSessionResponseDto> {
        val movieSession: MovieSession = movieSessionRequestDtoMapper.mapToModel(requestDto)
        movieSession.id = id
        return movieSessionService.update(movieSession)
            .map { movieSessionResponseDtoMapper.mapToDto(it) }
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: String
    ): Mono<DeleteResult> {
        return movieSessionService.delete(id)
    }
}
