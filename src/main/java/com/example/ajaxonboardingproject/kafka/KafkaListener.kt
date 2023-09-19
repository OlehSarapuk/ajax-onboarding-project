package com.example.ajaxonboardingproject.kafka

import com.example.ajaxonboardingproject.KafkaTopic
import io.nats.client.Connection
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaListener(
    val natsConnection: Connection
) {
    @KafkaListener(topics = [KafkaTopic.GET_FRESHLY_ADDED_CINEMA_HALLS], groupId = "foo")
    fun listenGroupFoo(msg: ByteArray) {
        natsConnection.publish("kafka", msg)
    }
}
