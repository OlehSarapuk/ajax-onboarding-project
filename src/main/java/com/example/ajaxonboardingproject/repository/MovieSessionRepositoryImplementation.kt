package com.example.ajaxonboardingproject.repository

import com.example.ajaxonboardingproject.model.MovieSession
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class MovieSessionRepositoryImplementation(
    private val mongoTemplate: MongoTemplate
): MovieSessionRepository {
    override fun findByMovieIdAndShowTimeAfter(id: String, date: LocalDateTime): List<MovieSession> {
        val query = Query()
            .addCriteria(Criteria.where("_id").`is`(id))
            .addCriteria(Criteria.where("showTime").gt(date))
        return mongoTemplate.find(query, MovieSession::class.java)
    }

    override fun save(movieSession: MovieSession): MovieSession {
        return mongoTemplate.save(movieSession)
    }

    override fun findById(id: String): MovieSession? {
        val query = Query()
            .addCriteria(Criteria.where("_id").`is`(id))
        return mongoTemplate.findOne(query, MovieSession::class.java)
    }

    override fun delete(movieSession: MovieSession) {
        mongoTemplate.remove(movieSession)
    }

    override fun findAll(): List<MovieSession> {
        return mongoTemplate.findAll(MovieSession::class.java)
    }
}
