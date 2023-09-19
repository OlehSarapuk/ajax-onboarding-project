package com.example.ajaxonboardingproject.kafka

import com.example.ajaxonboardingproject.KafkaTopic
import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaAdmin

@Configuration
class KafkaTopicConfig {
    @Value("\${spring.kafka.bootstrap-servers-config}")
    private lateinit var address: String

    @Bean
    fun kafkaAdmin(): KafkaAdmin {
        return KafkaAdmin(
            mapOf(
                Pair<String, Any>(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, address)
            )
        )
    }

    @Bean
    fun topic(): NewTopic {
        return NewTopic(KafkaTopic.GET_FRESHLY_ADDED_CINEMA_HALLS, 1, 1)
    }
}
