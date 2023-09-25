package com.example.ajaxonboardingproject.infrastructure.repository

import com.example.ajaxonboardingproject.application.repository.MovieRepository
import com.example.ajaxonboardingproject.domain.Movie
import com.example.ajaxonboardingproject.infrastructure.model.MovieEntity
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
        return mongoTemplate.save(mapToEntity(movie))
            .map { mapToDomain(it) }
    }

    override fun findAll(): Flux<Movie> {
        return mongoTemplate.findAll(MovieEntity::class.java)
            .map { mapToDomain(it) }
    }

    override fun findById(id: String): Mono<Movie> {
        val query = Query()
            .addCriteria(Criteria.where("_id").`is`(id))
        return mongoTemplate.findOne(query, MovieEntity::class.java)
            .map { mapToDomain(it) }
    }

    private fun mapToDomain(entity: MovieEntity): Movie {
        return Movie(
            description = entity.description,
            title = entity.title
        ).apply { id = entity.id }
    }

    private fun mapToEntity(domain: Movie): MovieEntity {
        return MovieEntity(
            description = domain.description,
            title = domain.title
        )
    }
}
