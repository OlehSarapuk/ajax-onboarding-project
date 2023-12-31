package com.example.ajaxonboardingproject.application.mapper

import com.example.ajaxonboardingproject.application.dto.MovieRequestDto
import com.example.ajaxonboardingproject.application.dto.MovieResponseDto
import com.example.ajaxonboardingproject.domain.Movie
import com.example.ajaxonboardingproject.dto.RequestDtoMapper
import com.example.ajaxonboardingproject.dto.ResponseDtoMapper
import org.springframework.stereotype.Component

@Component
class MovieMapper : RequestDtoMapper<MovieRequestDto, Movie>,
    ResponseDtoMapper<MovieResponseDto, Movie> {
    override fun mapToModel(dto: MovieRequestDto): Movie {
        return Movie(
            id = null,
            title = dto.title,
            description = dto.description
        )
    }

    override fun mapToDto(model: Movie): MovieResponseDto {
        return MovieResponseDto(
            id = model.id ?: throw NoSuchElementException("movie has no id"),
            title = model.title,
            description = model.description
        )
    }
}
