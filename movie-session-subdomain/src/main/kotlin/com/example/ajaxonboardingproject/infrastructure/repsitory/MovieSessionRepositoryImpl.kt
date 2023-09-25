package com.example.ajaxonboardingproject.infrastructure.repsitory

import com.example.ajaxonboardingproject.application.repository.MovieSessionRepository
import com.example.ajaxonboardingproject.domain.MovieSession
import com.example.ajaxonboardingproject.infrastructure.model.MovieSessionEntity
import com.mongodb.client.result.DeleteResult
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Repository
class MovieSessionRepositoryImpl(
    private val mongoTemplate: ReactiveMongoTemplate
) : MovieSessionRepository {

    override fun findByMovieIdAndShowTimeAfter(id: String, date: LocalDateTime): Flux<MovieSession> {
        val query = Query()
            .addCriteria(Criteria.where("_id").`is`(id))
            .addCriteria(Criteria.where("showTime").gt(date))
        return mongoTemplate.find(query, MovieSessionEntity::class.java)
            .map { mapToDomain(it) }
    }

    override fun save(movieSession: MovieSession): Mono<MovieSession> {
        return mongoTemplate.save(mapToEntity(movieSession))
            .map { mapToDomain(it) }
    }

    override fun findById(id: String): Mono<MovieSession> {
        val query = Query()
            .addCriteria(Criteria.where("_id").`is`(id))
        return mongoTemplate.findOne(query, MovieSessionEntity::class.java)
            .map { mapToDomain(it) }
    }

    override fun delete(movieSession: MovieSession): Mono<DeleteResult> {
        val movieSessionEntity = mapToEntity(movieSession).apply { id = movieSession.id }
        return mongoTemplate.remove(movieSessionEntity)
    }

    override fun findAll(): Flux<MovieSession> {
        return mongoTemplate.findAll(MovieSessionEntity::class.java)
            .map { mapToDomain(it) }
    }

    private fun mapToDomain(entity: MovieSessionEntity): MovieSession {
        return MovieSession(
            movie = entity.movie,
            cinemaHall = entity.cinemaHall,
            showTime = entity.showTime
        ).apply { id = entity.id }
    }

    private fun mapToEntity(domain: MovieSession): MovieSessionEntity {
        return MovieSessionEntity(
            movie = domain.movie,
            cinemaHall = domain.cinemaHall,
            showTime = domain.showTime
        )
    }
}
