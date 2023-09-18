package com.example.ajaxonboardingproject.service.proto.converter

import com.example.ajaxonboardingproject.MovieProto
import com.example.ajaxonboardingproject.MovieRequest
import com.example.ajaxonboardingproject.MovieResponse
import com.example.ajaxonboardingproject.model.Movie
import org.springframework.stereotype.Component

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
            title = movieProto.movie.title,
            description = movieProto.movie.description
        )
    }
}
