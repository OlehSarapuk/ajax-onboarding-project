package com.example.ajaxonboardingproject.service.mapper

import com.example.ajaxonboardingproject.dto.request.MovieSessionRequestDto
import com.example.ajaxonboardingproject.dto.response.MovieSessionResponseDto
import com.example.ajaxonboardingproject.model.MovieSession
import com.example.ajaxonboardingproject.service.CinemaHallService
import com.example.ajaxonboardingproject.service.MovieService
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
