package com.example.ajaxonboardingproject.moviesession.service.proto.converter

import com.example.ajaxonboardingproject.MovieSession as MovieSessionProto
import com.example.ajaxonboardingproject.MovieSessionResponse
import com.example.ajaxonboardingproject.cinemahall.service.proto.converter.CinemaHallConverter
import com.example.ajaxonboardingproject.movie.service.proto.converter.MovieConverter
import com.example.ajaxonboardingproject.moviesession.model.MovieSession
import org.springframework.stereotype.Component

@Component
class MovieSessionConverter(
    private val movieConverter: MovieConverter,
    private val cinemaHallConverter: CinemaHallConverter,
    private val localDateTimeConverter: LocalDateTimeConverter
) {
    fun movieSessionToProto(
        movieSession: MovieSession
    ): MovieSessionProto {
        return MovieSessionProto.newBuilder()
            .setMovie(movieConverter.movieToProto(movieSession.movie))
            .setCinemaHall(cinemaHallConverter.cinemaHallToProto(movieSession.cinemaHall))
            .setShowTime(localDateTimeConverter.localDateTimeToTimestamp(movieSession.showTime))
            .build()
    }

    fun protoToMovieSession(
        movieSessionProto: MovieSessionProto
    ): MovieSession {
        return MovieSession(
            movie = movieConverter.protoToMovie(movieSessionProto.movie),
            cinemaHall = cinemaHallConverter.protoToCinemaHall(movieSessionProto.cinemaHall),
            showTime = localDateTimeConverter.timestampToLocalDateTime(movieSessionProto.showTime)
        )
    }

    fun movieSessionToProtoResponse(
        movieSession: MovieSession
    ): MovieSessionResponse {
        return MovieSessionResponse.newBuilder()
            .setMovieSession(movieSessionToProto(movieSession))
            .build()
    }
}
