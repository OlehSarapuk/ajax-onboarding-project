package com.example.ajaxonboardingproject.controller

import com.example.ajaxonboardingproject.model.MovieSession
import com.example.ajaxonboardingproject.service.MovieSessionService
import com.example.ajaxonboardingproject.service.proto.converter.MovieSessionConverter
import io.nats.client.Connection
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component

@Component
class NatsMovieSessionAddController(
    private val movieSessionService: MovieSessionService,
    private val movieSessionConverter: MovieSessionConverter,
    private val natsConnection: Connection
) {
    @PostConstruct
    fun listenToNatsSubject() {
        val subject = "movieSession.add"
        natsConnection.subscribe(subject)
        val dispatcher = natsConnection.createDispatcher { message ->
            val receivedMessage = MovieSessionOuterClass.MovieSession.parseFrom(message.data)
            val response = add(receivedMessage)
            natsConnection.publish(message.replyTo, response.toByteArray())
        }
        dispatcher.subscribe(subject)
    }

    fun add(
        requestProto: MovieSessionOuterClass.MovieSession
    ): MovieSessionOuterClass.MovieSession {
        val movieSession: MovieSession =
            movieSessionService.add(movieSessionConverter.protoToMovieSession(requestProto))
        return movieSessionConverter.movieSessionToProto(movieSession)
    }
}

@Component
class NatsMovieSessionUpdateController(
    private val movieSessionService: MovieSessionService,
    private val movieSessionConverter: MovieSessionConverter,
    private val natsConnection: Connection
) {
    @PostConstruct
    fun listenToNatsSubject() {
        val subject = "movieSession.update.*"
        natsConnection.subscribe(subject)
        val dispatcher = natsConnection.createDispatcher { message ->
            val receivedMessage = MovieSessionOuterClass.MovieSession.parseFrom(message.data)
            val response = update(message.subject.split(".").last(), receivedMessage)
            natsConnection.publish(message.replyTo, response.toByteArray())
        }
        dispatcher.subscribe(subject)
    }

    fun update(
        requestId: String,
        requestProto: MovieSessionOuterClass.MovieSession
    ): MovieSessionOuterClass.MovieSession {
        val movieSession: MovieSession = movieSessionConverter.protoToMovieSession(requestProto)
        movieSession.id = requestId
        movieSessionService.update(movieSession)
        return movieSessionConverter.movieSessionToProto(movieSession)
    }
}

@Component
class NatsMovieSessionDeleteController(
    private val movieSessionService: MovieSessionService,
    private val natsConnection: Connection
) {
    @PostConstruct
    fun listenToNatsSubject() {
        val subject = "movieSession.delete.*"
        natsConnection.subscribe(subject)
        val dispatcher = natsConnection.createDispatcher { message ->
            val response = delete(message.subject.split(".").last())
            natsConnection.publish(message.replyTo, response.toByteArray())
        }
        dispatcher.subscribe(subject)
    }

    fun delete(
        id: String
    ): String {
        movieSessionService.delete(id)
        return "200"
    }
}
