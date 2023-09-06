package com.example.ajaxonboardingproject.service.proto.converter

import com.example.ajaxonboardingproject.model.MovieSession
import com.google.protobuf.Timestamp
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

private const val MILLISECONDS_PER_SECOND = 1000

@Component
class MovieSessionConverter(
    private val movieConverter: MovieConverter,
    private val cinemaHallConverter: CinemaHallConverter
) {
    fun movieSessionToProto(
        movieSession: MovieSession
    ): MovieSessionOuterClass.MovieSession {
        return MovieSessionOuterClass.MovieSession.newBuilder()
            .setMovie(movieConverter.movieToProto(movieSession.movie))
            .setCinemaHall(cinemaHallConverter.cinemaHallToProto(movieSession.cinemaHall))
            .setShowTime(localDateTimeToTimestamp(movieSession.showTime))
            .build()
    }

    fun protoToMovieSession(
        movieSessionProto: MovieSessionOuterClass.MovieSession
    ): MovieSession {
        return MovieSession(
            movie = movieConverter.protoToMovie(movieSessionProto.movie),
            cinemaHall = cinemaHallConverter.protoToCinemaHall(movieSessionProto.cinemaHall),
            showTime = timestampToLocalDateTime(movieSessionProto.showTime)
        )
    }

    private fun localDateTimeToTimestamp(
        dateTime: LocalDateTime
    ):Timestamp {
        return Timestamp.newBuilder()
            .setSeconds(dateTime.toInstant(ZoneOffset.UTC).toEpochMilli() / MILLISECONDS_PER_SECOND)
            .setNanos(dateTime.nano)
            .build()
    }

    private fun timestampToLocalDateTime(
        timestamp: Timestamp
    ): LocalDateTime {
        val instant = Instant.ofEpochSecond(timestamp.seconds, timestamp.nanos.toLong())
        return instant.atZone(ZoneId.of("UTC")).toLocalDateTime()
    }
}
