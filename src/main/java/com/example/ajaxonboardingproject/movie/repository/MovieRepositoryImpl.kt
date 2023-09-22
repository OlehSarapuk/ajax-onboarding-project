package com.example.ajaxonboardingproject.movie.repository

import com.example.ajaxonboardingproject.movie.model.Movie
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class MovieRepositoryImpl(
    private val mongoTemplate: ReactiveMongoTemplate
) : MovieRepository {

    override fun save(movie: Movie): Mono<Movie> {
        return mongoTemplate.save(movie)
    }

    override fun findAll(): Flux<Movie> {
        return mongoTemplate.findAll(Movie::class.java)
    }

    override fun findById(id: String): Mono<Movie> {
        val query = Query()
            .addCriteria(Criteria.where("_id").`is`(id))
        return mongoTemplate.findOne(query, Movie::class.java)
    }
}
