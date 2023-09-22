package com.example.ajaxonboardingproject.nats

import io.nats.client.Message
import io.nats.client.MessageHandler
import reactor.core.publisher.Mono
import reactor.core.scheduler.Scheduler

class ReactiveNatsHandler(
    private val natsController: NatsController<*, *>,
    private val scheduler: Scheduler
) : MessageHandler {

    override fun onMessage(message: Message) {
        natsController.handle(Mono.just(message))
            .map { it.toByteArray() }
            .doOnNext { natsController.connection.publish(message.replyTo, it) }
            .subscribeOn(scheduler)
            .subscribe()
    }
}
