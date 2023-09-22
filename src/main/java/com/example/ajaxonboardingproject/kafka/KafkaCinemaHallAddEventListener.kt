package com.example.ajaxonboardingproject.kafka

import com.example.ajaxonboardingproject.NatsSubject
import io.nats.client.Connection
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux

@Component
class KafkaCinemaHallAddEventListener(
    val natsConnection: Connection,
    val reactiveKafkaConsumerTemplate: ReactiveKafkaConsumerTemplate<String, ByteArray>
): CommandLineRunner {

    fun listen(): Flux<ByteArray> {
        return reactiveKafkaConsumerTemplate
            .receiveAutoAck()
            .map { it.value() }
            .doOnNext { natsConnection.publish(NatsSubject.KAFKA_GET_FRESHLY_ADDED_CINEMA_HALL_SUBJECT, it) }
    }

    override fun run(vararg args: String?) {
        listen().subscribe()
    }
}
