package com.example.ajaxonboardingproject.controller

import com.example.ajaxonboardingproject.dto.request.MovieRequestDto
import com.example.ajaxonboardingproject.dto.response.MovieResponseDto
import com.example.ajaxonboardingproject.model.Movie
import com.example.ajaxonboardingproject.service.MovieService
import com.example.ajaxonboardingproject.service.mapper.MovieMapper
import com.example.ajaxonboardingproject.service.mapper.mapToDto
import com.example.ajaxonboardingproject.service.mapper.mapToModel
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/movies")
data class MovieController(
        private val movieService: MovieService,
        private val movieMapper: MovieMapper) {
    @PostMapping
    fun add(
            @Valid @RequestBody requestDto : MovieRequestDto
    ) : MovieResponseDto {
        val movie : Movie = movieService.add(movieMapper.mapToModel((requestDto)))
        return movieMapper.mapToDto(movie)
    }

    @GetMapping
    fun getAll() : List<MovieResponseDto> {
        return movieService.getAll()
                .map(movieMapper::mapToDto)
                .toList()
    }
}
