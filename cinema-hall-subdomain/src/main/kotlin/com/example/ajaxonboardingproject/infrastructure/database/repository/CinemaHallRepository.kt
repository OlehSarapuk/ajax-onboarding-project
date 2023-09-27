package com.example.ajaxonboardingproject.infrastructure.database.repository

import com.example.ajaxonboardingproject.application.repository.CinemaHallRepositoryOutPort
import com.example.ajaxonboardingproject.domain.CinemaHall
import com.example.ajaxonboardingproject.infrastructure.database.model.CinemaHallEntity
import com.mongodb.client.result.DeleteResult
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class CinemaHallRepository(
    private val mongoTemplate: ReactiveMongoTemplate
) : CinemaHallRepositoryOutPort {

    override fun save(cinemaHall: CinemaHall): Mono<CinemaHall> {
        return mongoTemplate.save(cinemaHall.mapToEntity())
            .map { it.mapToDomain() }
    }

    override fun findAll(): Flux<CinemaHall> {
        return mongoTemplate.findAll(CinemaHallEntity::class.java)
            .map { it.mapToDomain() }
    }

    override fun findById(id: String): Mono<CinemaHall> {
        val query = Query()
            .addCriteria(Criteria.where("_id").`is`(id))
        return mongoTemplate.findOne(query, CinemaHallEntity::class.java)
            .map { it.mapToDomain() }
    }

    override fun deleteAll(): Mono<DeleteResult> {
        val query = Query()
        return mongoTemplate.remove(query, CinemaHallEntity::class.java)
    }

    private fun CinemaHallEntity.mapToDomain(): CinemaHall {
        return CinemaHall(
            id = this.id,
            description = this.description,
            capacity = this.capacity
        )
    }

    private fun CinemaHall.mapToEntity(): CinemaHallEntity {
        return CinemaHallEntity(
            description = this.description,
            capacity = this.capacity
        )
    }
}
