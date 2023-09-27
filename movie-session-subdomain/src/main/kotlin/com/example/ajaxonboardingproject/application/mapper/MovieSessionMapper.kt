package com.example.ajaxonboardingproject.application.mapper

import com.example.ajaxonboardingproject.application.service.CinemaHallInPort
import com.example.ajaxonboardingproject.application.service.MovieInPort
import com.example.ajaxonboardingproject.dto.RequestDtoMapper
import com.example.ajaxonboardingproject.dto.ResponseDtoMapper
import com.example.ajaxonboardingproject.domain.MovieSession
import com.example.ajaxonboardingproject.application.dto.MovieSessionRequestDto
import com.example.ajaxonboardingproject.application.dto.MovieSessionResponseDto
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class MovieSessionMapper(
    private val cinemaHallInPort: CinemaHallInPort,
    private val movieInPort: MovieInPort
) : RequestDtoMapper<MovieSessionRequestDto, Mono<MovieSession>>,
    ResponseDtoMapper<MovieSessionResponseDto, MovieSession> {
    override fun mapToModel(dto: MovieSessionRequestDto): Mono<MovieSession> {
        val movieMono = movieInPort.get(dto.movieId)
        val cinemaHallMono = cinemaHallInPort.get(dto.cinemaHallId)
        return Mono.zip(movieMono, cinemaHallMono) { movie, cinemaHall ->
            MovieSession(
                id = null,
                movie = movie,
                cinemaHall = cinemaHall,
                showTime = dto.showTime
            )
        }
    }

    override fun mapToDto(model: MovieSession): MovieSessionResponseDto {
        return MovieSessionResponseDto(
            movieSessionId = model.id ?: throw NoSuchElementException("movie session has no id"),
            cinemaHallId = model.cinemaHall.id ?: throw NoSuchElementException("cinema hall has no id"),
            movieTitle = model.movie.title,
            movieId = model.movie.id ?: throw NoSuchElementException("movie has no id"),
            showTime = model.showTime
        )
    }
}
