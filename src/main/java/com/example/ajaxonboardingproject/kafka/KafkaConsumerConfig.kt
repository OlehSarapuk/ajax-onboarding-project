package com.example.ajaxonboardingproject.kafka

import com.example.ajaxonboardingproject.KafkaTopic
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.Deserializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate
import reactor.kafka.receiver.ReceiverOptions
import java.util.Collections

@EnableKafka
@Configuration
class ReactiveKafkaConsumerConfig {
    @Bean
    fun kafkaReceiverOptions(
        kafkaProperties: KafkaProperties
    ): ReceiverOptions<String, ByteArray> {
        val basicReceiverOptions: ReceiverOptions<String, ByteArray> =
            ReceiverOptions.create(
                mapOf(
                    Pair(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092"),
                    Pair(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java),
                    Pair(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ProtobufDeserializer::class.java),
                    Pair(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"),
                    Pair(ConsumerConfig.GROUP_ID_CONFIG, "ajax")
                )
            )
        return basicReceiverOptions.subscription(Collections.singletonList(KafkaTopic.GET_FRESHLY_ADDED_CINEMA_HALLS))
    }

    @Bean
    fun reactiveKafkaConsumerTemplate(
        kafkaReceiverOptions: ReceiverOptions<String, ByteArray>
    ): ReactiveKafkaConsumerTemplate<String, ByteArray> {
        return ReactiveKafkaConsumerTemplate(kafkaReceiverOptions)
    }
}

class ProtobufDeserializer : Deserializer<ByteArray> {

    override fun deserialize(topic: String, data: ByteArray): ByteArray {
        return data
    }
}
