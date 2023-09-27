package com.example.ajaxonboardingproject.infrastructure.database.repsitory

import com.example.ajaxonboardingproject.application.repository.MovieSessionRepositoryOutPort
import com.example.ajaxonboardingproject.domain.MovieSession
import com.example.ajaxonboardingproject.infrastructure.database.model.MovieSessionEntity
import com.mongodb.client.result.DeleteResult
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Repository
class MovieSessionRepository(
    private val mongoTemplate: ReactiveMongoTemplate
) : MovieSessionRepositoryOutPort {

    override fun findByMovieIdAndShowTimeAfter(id: String, date: LocalDateTime): Flux<MovieSession> {
        val query = Query()
            .addCriteria(Criteria.where("_id").`is`(id))
            .addCriteria(Criteria.where("showTime").gt(date))
        return mongoTemplate.find(query, MovieSessionEntity::class.java)
            .map { it.mapToDomain() }
    }

    override fun save(movieSession: MovieSession): Mono<MovieSession> {
        return mongoTemplate.save(movieSession.mapToEntity())
            .map { it.mapToDomain() }
    }

    override fun findById(id: String): Mono<MovieSession> {
        val query = Query()
            .addCriteria(Criteria.where("_id").`is`(id))
        return mongoTemplate.findOne(query, MovieSessionEntity::class.java)
            .map { it.mapToDomain() }
    }

    override fun delete(movieSession: MovieSession): Mono<DeleteResult> {
        val movieSessionEntity = movieSession.mapToEntity()
        movieSession.id?.let { movieSessionEntity.id = it }
        return mongoTemplate.remove(movieSessionEntity)
    }

    override fun findAll(): Flux<MovieSession> {
        return mongoTemplate.findAll(MovieSessionEntity::class.java)
            .map { it.mapToDomain() }
    }

    private fun MovieSessionEntity.mapToDomain(): MovieSession {
        return MovieSession(
            id = this.id,
            movie = this.movie,
            cinemaHall = this.cinemaHall,
            showTime = this.showTime
        )
    }

    private fun MovieSession.mapToEntity(): MovieSessionEntity {
        return MovieSessionEntity(
            movie = this.movie,
            cinemaHall = this.cinemaHall,
            showTime = this.showTime
        )
    }
}
