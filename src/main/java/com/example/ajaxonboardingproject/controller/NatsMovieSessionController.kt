package com.example.ajaxonboardingproject.controller

import MovieSessionOuterClass
import com.example.ajaxonboardingproject.model.MovieSession
import com.example.ajaxonboardingproject.service.MovieSessionService
import com.example.ajaxonboardingproject.service.proto.converter.MovieSessionConverter
import com.google.protobuf.Parser
import io.nats.client.Connection
import io.nats.client.Message
import org.springframework.stereotype.Component

@Component
class NatsMovieSessionAddController(
    private val service: MovieSessionService,
    private val converter: MovieSessionConverter,
    override val connection: Connection
) : NatsController<MovieSessionOuterClass.MovieSession> {
    override val subject: String = "movieSession.add"
    override val parser: Parser<MovieSessionOuterClass.MovieSession> =
        MovieSessionOuterClass.MovieSession.parser()

    fun generateReplyForNatsRequest(
        message: Message
    ): ByteArray {
        val requestProto = MovieSessionOuterClass.MovieSession.parseFrom(message.data)
        val movieSession: MovieSession =
            service.add(converter.protoToMovieSession(requestProto))
        return converter.movieSessionToProto(movieSession).toByteArray()
    }
}

@Component
class NatsMovieSessionUpdateController(
    private val service: MovieSessionService,
    private val converter: MovieSessionConverter,
    override val connection: Connection
) : NatsController<MovieSessionOuterClass.MovieSession> {
    override val subject: String = "movieSession.update.*"
    override val parser: Parser<MovieSessionOuterClass.MovieSession> =
        MovieSessionOuterClass.MovieSession.parser()

    fun generateReplyForNatsRequest(
        message: Message
    ): ByteArray {
        val requestProto = MovieSessionOuterClass.MovieSession.parseFrom(message.data)
        val movieSession: MovieSession = converter.protoToMovieSession(requestProto)
        movieSession.id = message.subject.split(".").last()
        service.update(movieSession)
        return converter.movieSessionToProto(movieSession).toByteArray()
    }
}

@Component
class NatsMovieSessionDeleteController(
    private val service: MovieSessionService,
    override val connection: Connection
) : NatsController<MovieSessionOuterClass.MovieSession> {
    override val subject: String = "movieSession.delete.*"
    override val parser: Parser<MovieSessionOuterClass.MovieSession> =
        MovieSessionOuterClass.MovieSession.parser()

    fun generateReplyForNatsRequest(
        message: Message
    ): ByteArray {
        service.delete(message.subject.split(".").last())
        return "200".toByteArray()
    }
}
