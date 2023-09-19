package com.example.ajaxonboardingproject.kafka

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.Deserializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory

@EnableKafka
@Configuration
class KafkaConsumerConfig {
    @Value("\${spring.kafka.bootstrap-servers-config}")
    private lateinit var address: String

    @Bean
    fun consumerFactory(): ConsumerFactory<String, ByteArray> {
        return DefaultKafkaConsumerFactory(
            mapOf(
                Pair(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, address),
                Pair(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java),
                Pair(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ProtobufDeserializer::class.java)
            )
        )
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, ByteArray> {
        return ConcurrentKafkaListenerContainerFactory<String, ByteArray>().apply {
            consumerFactory = consumerFactory()
        }
    }
}

class ProtobufDeserializer : Deserializer<ByteArray> {

    override fun deserialize(topic: String, data: ByteArray): ByteArray {
        return data
    }
}
