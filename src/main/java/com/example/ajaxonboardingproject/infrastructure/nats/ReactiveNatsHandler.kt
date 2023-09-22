package com.example.ajaxonboardingproject.infrastructure.nats

import io.nats.client.Message
import io.nats.client.MessageHandler
import org.springframework.stereotype.Component
import reactor.core.scheduler.Scheduler

class ReactiveNatsHandler(
    private val natsController: NatsController<*, *>,
    private val scheduler: Scheduler
) : MessageHandler {

    override fun onMessage(message: Message) {
        natsController.handle(message)
            .map { it.toByteArray() }
            .doOnNext { natsController.connection.publish(message.replyTo, it) }
            .subscribeOn(scheduler)
            .subscribe()
    }
}
