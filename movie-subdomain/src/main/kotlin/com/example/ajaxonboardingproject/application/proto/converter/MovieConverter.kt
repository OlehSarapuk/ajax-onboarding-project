package com.example.ajaxonboardingproject.application.proto.converter

import com.example.ajaxonboardingproject.MovieRequest
import com.example.ajaxonboardingproject.MovieResponse
import com.example.ajaxonboardingproject.domain.Movie
import org.springframework.stereotype.Component
import com.example.ajaxonboardingproject.Movie as MovieProto

@Component
class MovieConverter {
    fun movieToProto(
        movie: Movie
    ): MovieProto {
        return MovieProto.newBuilder()
            .setTitle(movie.title)
            .setDescription(movie.description)
            .build()
    }

    fun protoToMovie(
        movieProto: MovieProto
    ): Movie {
        return Movie(
            id = null,
            title = movieProto.title,
            description = movieProto.description
        )
    }

    fun movieToProtoResponse(
        movie: Movie
    ): MovieResponse {
        return MovieResponse.newBuilder()
            .setMovie(movieToProto(movie))
            .build()
    }

    fun protoRequestToMovie(
        movieProto: MovieRequest
    ): Movie {
        return Movie(
            id = null,
            title = movieProto.movie.title,
            description = movieProto.movie.description
        )
    }
}
