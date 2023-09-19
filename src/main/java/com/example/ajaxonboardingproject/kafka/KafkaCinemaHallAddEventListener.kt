package com.example.ajaxonboardingproject.kafka

import com.example.ajaxonboardingproject.KafkaTopic
import com.example.ajaxonboardingproject.NatsSubject
import io.nats.client.Connection
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaCinemaHallAddEventListener(
    val natsConnection: Connection
) {
    @KafkaListener(topics = [KafkaTopic.GET_FRESHLY_ADDED_CINEMA_HALLS], groupId = "ajax")
    fun listenGroupFoo(msg: ByteArray) {
        natsConnection.publish(NatsSubject.KAFKA_GET_FRESHLY_ADDED_CINEMA_HALL_SUBJECT, msg)
    }
}
