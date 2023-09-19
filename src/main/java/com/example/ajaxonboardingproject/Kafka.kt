package com.example.ajaxonboardingproject

import jakarta.annotation.PostConstruct
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class Kafka (
    private val kafkaTemplate: KafkaTemplate<String, String>
) {
    @KafkaListener(topics = ["topic"], groupId = "foo")
    fun listenGroupFoo() {
        println("Received Message in group foo: fffffoooooooo")
    }
}