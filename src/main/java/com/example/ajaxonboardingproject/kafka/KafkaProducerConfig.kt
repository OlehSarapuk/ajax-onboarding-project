package com.example.ajaxonboardingproject.kafka

import com.google.protobuf.GeneratedMessageV3
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.Serializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import reactor.kafka.sender.SenderOptions

@Configuration
class ReactiveKafkaProducerConfig {
    @Bean
    fun reactiveKafkaProducerTemplate(
        properties: KafkaProperties
    ): ReactiveKafkaProducerTemplate<String, GeneratedMessageV3> {
        val senderOptions: SenderOptions<String, GeneratedMessageV3> =
            SenderOptions.create(
                mapOf(
                    Pair(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092"),
                    Pair(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java),
                    Pair(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ProtobufSerializer::class.java)
                )
            )
        return ReactiveKafkaProducerTemplate(senderOptions)
    }
}

class ProtobufSerializer<T : GeneratedMessageV3> : Serializer<T> {

    override fun serialize(topic: String, data: T): ByteArray {
        return data.toByteArray()
    }
}
