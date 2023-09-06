package com.example.ajaxonboardingproject.service.proto.converter

import com.example.ajaxonboardingproject.model.Movie
import org.springframework.stereotype.Component

@Component
class MovieConverter {
    fun movieToProto(
        movie: Movie
    ): MovieOuterClass.Movie {
        return MovieOuterClass.Movie.newBuilder()
            .setTitle(movie.title)
            .setDescription(movie.description)
            .build()
    }

    fun protoToMovie(
        movieProto: MovieOuterClass.Movie)
    : Movie {
        return Movie(
            title = movieProto.title,
            description = movieProto.description
        )
    }
}
