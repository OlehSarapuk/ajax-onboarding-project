package com.example.ajaxonboardingproject.application.mapper

import com.example.ajaxonboardingproject.application.service.CinemaHallService
import com.example.ajaxonboardingproject.application.service.MovieService
import com.example.ajaxonboardingproject.dto.RequestDtoMapper
import com.example.ajaxonboardingproject.dto.ResponseDtoMapper
import com.example.ajaxonboardingproject.domain.MovieSession
import com.example.ajaxonboardingproject.application.dto.MovieSessionRequestDto
import com.example.ajaxonboardingproject.application.dto.MovieSessionResponseDto
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class MovieSessionMapper(
    private val cinemaHallService: CinemaHallService,
    private val movieService: MovieService
) : RequestDtoMapper<MovieSessionRequestDto, Mono<MovieSession>>,
    ResponseDtoMapper<MovieSessionResponseDto, MovieSession> {
    override fun mapToModel(dto: MovieSessionRequestDto): Mono<MovieSession> {
        val movieMono = movieService.get(dto.movieId)
        val cinemaHallMono = cinemaHallService.get(dto.cinemaHallId)
        return Mono.zip(movieMono, cinemaHallMono) { movie, cinemaHall ->
            MovieSession(
                movie = movie,
                cinemaHall = cinemaHall,
                showTime = dto.showTime
            )
        }
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
