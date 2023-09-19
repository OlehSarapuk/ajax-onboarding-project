package com.example.ajaxonboardingproject.kafka

import com.google.protobuf.GeneratedMessageV3
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.Serializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@Configuration
class KafkaProducerConfig {
    @Bean
    fun producerFactory(): ProducerFactory<String, GeneratedMessageV3> {
        return DefaultKafkaProducerFactory(
            mapOf(
                Pair(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092"),
                Pair(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java),
                Pair(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ProtobufSerializer::class.java)
            )
        )
    }

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, GeneratedMessageV3> {
        return KafkaTemplate(producerFactory())
    }
}

class ProtobufSerializer<T : GeneratedMessageV3> : Serializer<T> {

    override fun serialize(topic: String, data: T): ByteArray {
        return data.toByteArray()
    }
}
