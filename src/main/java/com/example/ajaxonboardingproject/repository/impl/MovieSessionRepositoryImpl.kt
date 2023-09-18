package com.example.ajaxonboardingproject.repository.impl

import com.example.ajaxonboardingproject.model.MovieSession
import com.example.ajaxonboardingproject.repository.MovieSessionRepository
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
        return mongoTemplate.find(query, MovieSession::class.java)
    }

    override fun save(movieSession: MovieSession): Mono<MovieSession> {
        return mongoTemplate.save(movieSession)
    }

    override fun findById(id: String): Mono<MovieSession> {
        val query = Query()
            .addCriteria(Criteria.where("_id").`is`(id))
        return mongoTemplate.findOne(query, MovieSession::class.java)
    }

    override fun delete(movieSession: MovieSession): Mono<DeleteResult> {
        return mongoTemplate.remove(movieSession)
    }

    override fun findAll(): Flux<MovieSession> {
        return mongoTemplate.findAll(MovieSession::class.java)
    }
}
