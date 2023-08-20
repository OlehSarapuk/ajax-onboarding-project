package com.example.ajaxonboardingproject.service.mapper

import com.example.ajaxonboardingproject.dto.request.MovieSessionRequestDto
import com.example.ajaxonboardingproject.dto.response.MovieSessionResponseDto
import com.example.ajaxonboardingproject.model.MovieSession
import com.example.ajaxonboardingproject.service.CinemaHallService
import com.example.ajaxonboardingproject.service.MovieService
import org.springframework.stereotype.Component

@Component
class MovieSessionMapper (
        private val cinemaHallService: CinemaHallService,
        private val movieService: MovieService) : RequestDtoMapper<MovieSessionRequestDto, MovieSession>,
        ResponseDtoMapper<MovieSessionResponseDto, MovieSession> {
    override fun mapToModel(dto: MovieSessionRequestDto): MovieSession {
        return MovieSession(
                movie = movieService.get(dto.movieId),
                cinemaHall = cinemaHallService.get(dto.cinemaHallId),
                showTime = dto.showTime)
    }

    override fun mapToDto(model : MovieSession): MovieSessionResponseDto {
        return MovieSessionResponseDto(
                movieSessionId = model.id!!,
                cinemaHallId = model.cinemaHall.id!!,
                movieTitle = model.movie.title,
                movieId = model.movie.id!!,
                showTime = model.showTime)
    }
}