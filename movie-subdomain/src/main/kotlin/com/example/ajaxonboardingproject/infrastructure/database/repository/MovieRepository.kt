package com.example.ajaxonboardingproject.infrastructure.database.repository

import com.example.ajaxonboardingproject.application.repository.MovieRepositoryOutPort
import com.example.ajaxonboardingproject.domain.Movie
import com.example.ajaxonboardingproject.infrastructure.database.model.MovieEntity
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class MovieRepository(
    private val mongoTemplate: ReactiveMongoTemplate
) : MovieRepositoryOutPort {

    override fun save(movie: Movie): Mono<Movie> {
        return mongoTemplate.save(movie.mapToEntity())
            .map { it.mapToDomain() }
    }

    override fun findAll(): Flux<Movie> {
        return mongoTemplate.findAll(MovieEntity::class.java)
            .map { it.mapToDomain() }
    }

    override fun findById(id: String): Mono<Movie> {
        val query = Query()
            .addCriteria(Criteria.where("_id").`is`(id))
        return mongoTemplate.findOne(query, MovieEntity::class.java)
            .map { it.mapToDomain() }
    }

    private fun MovieEntity.mapToDomain(): Movie {
        return Movie(
            id = this.id,
            description = this.description,
            title = this.title
        )
    }

    private fun Movie.mapToEntity(): MovieEntity {
        return MovieEntity(
            description = this.description,
            title = this.title
        )
    }
}
