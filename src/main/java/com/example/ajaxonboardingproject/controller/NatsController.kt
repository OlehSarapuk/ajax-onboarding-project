package com.example.ajaxonboardingproject.controller

import com.google.protobuf.Parser
import io.nats.client.Connection

interface NatsController<T> {
    val subject: String
    val connection: Connection
    val parser: Parser<T>
}
