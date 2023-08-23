package com.example.ajaxonboardingproject.controller

import com.example.ajaxonboardingproject.dto.request.MovieSessionRequestDto
import com.example.ajaxonboardingproject.dto.response.MovieSessionResponseDto
import com.example.ajaxonboardingproject.model.MovieSession
import com.example.ajaxonboardingproject.service.MovieSessionService
import com.example.ajaxonboardingproject.service.mapper.MovieSessionMapper
import com.example.ajaxonboardingproject.service.mapper.mapToDto
import com.example.ajaxonboardingproject.service.mapper.mapToModel
import com.example.ajaxonboardingproject.util.DATE_PATTERN
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
import java.time.LocalDate

@RestController
@RequestMapping("/movie-sessions")
data class MovieSessionController(
        private val movieSessionService: MovieSessionService,
        private val movieSessionMapper: MovieSessionMapper) {
    @PostMapping
    fun add(
            @Valid @RequestBody requestDto: MovieSessionRequestDto
    ) : MovieSessionResponseDto {
        val movieSession : MovieSession = movieSessionMapper.mapToModel(requestDto)
        movieSessionService.add(movieSession)
        return movieSessionMapper.mapToDto(movieSession)
    }

    @GetMapping("/available")
    fun findAvailableSessions(
            @RequestParam movieId : Long,
            @RequestParam @DateTimeFormat(pattern = DATE_PATTERN) date : LocalDate
    ): List<MovieSessionResponseDto>{
        return movieSessionService.findAvailableSessions(movieId, date)
                .map(movieSessionMapper::mapToDto)
                .toList()
    }

    @PutMapping("/{id}")
    fun update(
            @PathVariable id : java.lang.Long,
            @Valid @RequestBody requestDto: MovieSessionRequestDto
    ) : MovieSessionResponseDto{
        val movieSession : MovieSession = movieSessionMapper.mapToModel(requestDto)
        movieSession.id = id
        movieSessionService.update(movieSession)
        return movieSessionMapper.mapToDto(movieSession)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id : Long) = movieSessionService.delete(id)
}
