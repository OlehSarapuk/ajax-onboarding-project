package com.example.ajaxonboardingproject.service.mapper;

import com.example.ajaxonboardingproject.dto.request.MovieSessionRequestDto;
import com.example.ajaxonboardingproject.dto.response.MovieSessionResponseDto;
import com.example.ajaxonboardingproject.model.MovieSession;
import com.example.ajaxonboardingproject.service.CinemaHallService;
import com.example.ajaxonboardingproject.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MovieSessionMapper implements RequestDtoMapper<MovieSessionRequestDto, MovieSession>,
        ResponseDtoMapper<MovieSessionResponseDto, MovieSession> {
    private final CinemaHallService cinemaHallService;
    private final MovieService movieService;

    @Override
    public MovieSession mapToModel(MovieSessionRequestDto dto) {
        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(movieService.get(dto.getMovieId()));
        movieSession.setCinemaHall(cinemaHallService.get(dto.getCinemaHallId()));
        movieSession.setShowTime(dto.getShowTime());
        return movieSession;
    }

    @Override
    public MovieSessionResponseDto mapToDto(MovieSession movieSession) {
        return new MovieSessionResponseDto(
                movieSession.getId(),
                movieSession.getCinemaHall().getId(),
                movieSession.getMovie().getTitle(),
                movieSession.getMovie().getId(),
                movieSession.getShowTime());
    }
}
