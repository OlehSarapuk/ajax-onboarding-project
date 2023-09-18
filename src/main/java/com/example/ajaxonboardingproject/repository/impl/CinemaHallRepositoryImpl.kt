package com.example.ajaxonboardingproject.repository.impl

import com.example.ajaxonboardingproject.model.CinemaHall
import com.example.ajaxonboardingproject.repository.CinemaHallRepository
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
        return mongoTemplate.save(cinemaHall)
    }

    override fun findAll(): Flux<CinemaHall> {
        return mongoTemplate.findAll(CinemaHall::class.java)
    }

    override fun findById(id: String): Mono<CinemaHall> {
        val query = Query()
            .addCriteria(Criteria.where("_id").`is`(id))
        return mongoTemplate.findOne(query, CinemaHall::class.java)
    }

    override fun deleteAll(): Mono<DeleteResult> {
        val query = Query()
        return mongoTemplate.remove(query, CinemaHall::class.java)
    }
}
