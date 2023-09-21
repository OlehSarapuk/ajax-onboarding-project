package com.example.ajaxonboardingproject.nats

import com.google.protobuf.GeneratedMessageV3
import com.google.protobuf.Parser
import io.nats.client.Connection
import io.nats.client.Message
import reactor.core.publisher.Mono

interface NatsController<ReqT : GeneratedMessageV3, RespT: GeneratedMessageV3> {

    val subject: String

    val connection: Connection

    val parser: Parser<ReqT>

    fun generateReplyForNatsRequest(request: Mono<ReqT>): Mono<RespT>

    fun handle(msg: Mono<Message>): Mono<RespT> {
        val request = msg
            .map { parser.parseFrom(it.data) }
        return generateReplyForNatsRequest(request)
    }
}
