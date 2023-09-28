package com.example.ajaxonboardingproject.infrastructure.database.redis.repository

import com.example.ajaxonboardingproject.application.repository.RedisCinemaHallRepositoryOutPort
import com.example.ajaxonboardingproject.domain.CinemaHall
import com.example.ajaxonboardingproject.infrastructure.database.model.CinemaHallEntity
import com.example.ajaxonboardingproject.infrastructure.database.mongo.repository.mapToDomain
import com.example.ajaxonboardingproject.infrastructure.database.mongo.repository.mapToEntity
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class RedisCinemaHallRepository(
    private val redisTemplate: ReactiveRedisTemplate<String, CinemaHallEntity>
) : RedisCinemaHallRepositoryOutPort {

    override fun findById(id: String): Mono<CinemaHall> {
        return redisTemplate.opsForValue().get(id)
            .map { it.mapToDomain() }
    }

    override fun save(cinemaHall: CinemaHall): Mono<CinemaHall> {
        cinemaHall.id ?: throw NoSuchElementException("cinema hall has no id")
        return redisTemplate.opsForValue()
            .set(cinemaHall.id, cinemaHall.mapToEntity().apply { id = cinemaHall.id })
            .map { cinemaHall }
    }
}
