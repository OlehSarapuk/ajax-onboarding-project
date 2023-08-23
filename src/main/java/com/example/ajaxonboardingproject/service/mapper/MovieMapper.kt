package com.example.ajaxonboardingproject.service.mapper

import com.example.ajaxonboardingproject.dto.request.MovieRequestDto
import com.example.ajaxonboardingproject.dto.response.MovieResponseDto
import com.example.ajaxonboardingproject.model.Movie
import org.springframework.stereotype.Component

fun MovieMapper.mapToModel(dto: MovieRequestDto): Movie {
        return Movie(
                title = dto.title,
                description = dto.description
        )
}

fun MovieMapper.mapToDto(model : Movie): MovieResponseDto {
        return MovieResponseDto(
                id = model.id!!,
                title = model.title,
                description = model.description
            )
}

@Component
class MovieMapper
