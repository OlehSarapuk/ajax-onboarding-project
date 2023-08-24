package com.example.ajaxonboardingproject.service.mapper

import com.example.ajaxonboardingproject.dto.request.MovieSessionRequestDto
import com.example.ajaxonboardingproject.dto.response.MovieSessionResponseDto
import com.example.ajaxonboardingproject.model.MovieSession
import com.example.ajaxonboardingproject.service.CinemaHallService
import com.example.ajaxonboardingproject.service.MovieService
import org.springframework.stereotype.Component

fun MovieSessionMapper.mapToModel(dto: MovieSessionRequestDto): MovieSession {
    return MovieSession(
        movie = this.movieService.get(dto.movieId),
        cinemaHall = this.cinemaHallService.get(dto.cinemaHallId),
        showTime = dto.showTime
    )
}

fun MovieSessionMapper.mapToDto(model: MovieSession): MovieSessionResponseDto {
    return MovieSessionResponseDto(
        movieSessionId = model.id,
        cinemaHallId = model.cinemaHall.id,
        movieTitle = model.movie.title,
        movieId = model.movie.id,
        showTime = model.showTime
    )
}

@Component
class MovieSessionMapper(
    val cinemaHallService: CinemaHallService,
    val movieService: MovieService
)
