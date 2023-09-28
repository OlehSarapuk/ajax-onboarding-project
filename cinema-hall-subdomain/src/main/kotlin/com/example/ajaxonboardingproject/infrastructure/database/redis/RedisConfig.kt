package com.example.ajaxonboardingproject.infrastructure.database.redis

import com.example.ajaxonboardingproject.infrastructure.database.model.CinemaHallEntity
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {
    @Bean
    fun reactiveRedisTemplate(
        factory: ReactiveRedisConnectionFactory
    ) : ReactiveRedisTemplate<String, CinemaHallEntity> {
        val valueSerializer = Jackson2JsonRedisSerializer(CinemaHallEntity::class.java)
        val builder: RedisSerializationContext.RedisSerializationContextBuilder<String, CinemaHallEntity> =
            RedisSerializationContext.newSerializationContext(StringRedisSerializer())
        val context = builder.value(valueSerializer).build()
        return ReactiveRedisTemplate(factory, context)
    }
}