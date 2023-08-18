package com.example.ajaxonboardingproject.service.mapper;

import com.example.ajaxonboardingproject.dto.request.MovieRequestDto;
import com.example.ajaxonboardingproject.dto.response.MovieResponseDto;
import com.example.ajaxonboardingproject.model.Movie;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper implements RequestDtoMapper<MovieRequestDto, Movie>,
        ResponseDtoMapper<MovieResponseDto, Movie> {
    @Override
    public Movie mapToModel(MovieRequestDto dto) {
        Movie movie = new Movie();
        movie.setTitle(dto.getTitle());
        movie.setDescription(dto.getDescription());
        return movie;
    }

    @Override
    public MovieResponseDto mapToDto(Movie movie) {
        return new MovieResponseDto(
        movie.getId(),
        movie.getTitle(),
        movie.getDescription());
    }
}
