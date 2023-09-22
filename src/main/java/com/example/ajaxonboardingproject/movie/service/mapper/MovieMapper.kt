package com.example.ajaxonboardingproject.movie.service.mapper

import com.example.ajaxonboardingproject.infrastructure.dto.RequestDtoMapper
import com.example.ajaxonboardingproject.infrastructure.dto.ResponseDtoMapper
import com.example.ajaxonboardingproject.movie.dto.MovieRequestDto
import com.example.ajaxonboardingproject.movie.dto.MovieResponseDto
import com.example.ajaxonboardingproject.movie.model.Movie
import org.springframework.stereotype.Component

@Component
class MovieMapper : RequestDtoMapper<MovieRequestDto, Movie>,
    ResponseDtoMapper<MovieResponseDto, Movie> {
    override fun mapToModel(dto: MovieRequestDto): Movie {
        return Movie(
            title = dto.title,
            description = dto.description
        )
    }

    override fun mapToDto(model: Movie): MovieResponseDto {
        return MovieResponseDto(
            id = model.id,
            title = model.title,
            description = model.description
        )
    }
}
