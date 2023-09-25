package com.example.ajaxonboardingproject.infrastructure.repository

import com.example.ajaxonboardingproject.application.repository.CinemaHallRepository
import com.example.ajaxonboardingproject.domain.CinemaHall
import com.example.ajaxonboardingproject.infrastructure.model.CinemaHallEntity
import com.mongodb.client.result.DeleteResult
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class CinemaHallRepositoryImpl(
    private val mongoTemplate: ReactiveMongoTemplate
) : CinemaHallRepository {

    override fun save(cinemaHall: CinemaHall): Mono<CinemaHall> {
        return mongoTemplate.save(mapToEntity(cinemaHall))
            .map { mapToDomain(it) }
    }

    override fun findAll(): Flux<CinemaHall> {
        return mongoTemplate.findAll(CinemaHallEntity::class.java)
            .map { mapToDomain(it) }
    }

    override fun findById(id: String): Mono<CinemaHall> {
        val query = Query()
            .addCriteria(Criteria.where("_id").`is`(id))
        return mongoTemplate.findOne(query, CinemaHallEntity::class.java)
            .map { mapToDomain(it) }
    }

    override fun deleteAll(): Mono<DeleteResult> {
        val query = Query()
        return mongoTemplate.remove(query, CinemaHallEntity::class.java)
    }

    private fun mapToDomain(entity: CinemaHallEntity): CinemaHall {
        return CinemaHall(
            description = entity.description,
            capacity = entity.capacity
        ).apply { id = entity.id }
    }

    private fun mapToEntity(domain: CinemaHall): CinemaHallEntity {
        return CinemaHallEntity(
            description = domain.description,
            capacity = domain.capacity
        )
    }
}
