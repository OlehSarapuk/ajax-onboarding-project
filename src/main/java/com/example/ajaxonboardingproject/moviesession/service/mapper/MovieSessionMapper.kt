package com.example.ajaxonboardingproject.moviesession.service.mapper

import com.example.ajaxonboardingproject.cinemahall.service.CinemaHallService
import com.example.ajaxonboardingproject.movie.service.MovieService
import com.example.ajaxonboardingproject.infrastructure.dto.RequestDtoMapper
import com.example.ajaxonboardingproject.infrastructure.dto.ResponseDtoMapper
import com.example.ajaxonboardingproject.moviesession.model.MovieSession
import com.example.ajaxonboardingproject.moviesession.dto.MovieSessionRequestDto
import com.example.ajaxonboardingproject.moviesession.dto.MovieSessionResponseDto
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class MovieSessionMapper(
    private val cinemaHallService: CinemaHallService,
    private val movieService: MovieService
) : RequestDtoMapper<MovieSessionRequestDto, MovieSession>,
    ResponseDtoMapper<MovieSessionResponseDto, MovieSession> {
    override fun mapToModel(dto: MovieSessionRequestDto): MovieSession {
        val movieMono = movieService.get(dto.movieId)
        val cinemaHallMono = cinemaHallService.get(dto.cinemaHallId)
        return Mono.zip(movieMono, cinemaHallMono) { movie, cinemaHall ->
            MovieSession(
                movie = movie,
                cinemaHall = cinemaHall,
                showTime = dto.showTime
            )
        }.block()!!
    }

    override fun mapToDto(model: MovieSession): MovieSessionResponseDto {
        return MovieSessionResponseDto(
            movieSessionId = model.id,
            cinemaHallId = model.cinemaHall.id,
            movieTitle = model.movie.title,
            movieId = model.movie.id,
            showTime = model.showTime
        )
    }
}
