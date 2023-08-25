package com.example.ajaxonboardingproject.service.mapper

import com.example.ajaxonboardingproject.dto.request.MovieRequestDto
import com.example.ajaxonboardingproject.dto.response.MovieResponseDto
import com.example.ajaxonboardingproject.model.Movie
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
