package com.example.ajaxonboardingproject.user.repository

import com.example.ajaxonboardingproject.shoppingcart.model.ShoppingCart
import com.example.ajaxonboardingproject.user.model.User
import com.mongodb.client.result.DeleteResult
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class UserRepositoryImpl(
    private val mongoTemplate: ReactiveMongoTemplate
) : UserRepository {

    override fun findByEmail(email: String): Mono<User> {
        val query = Query()
            .addCriteria(Criteria.where("email").`is`(email))
        return mongoTemplate.findOne(query, User::class.java)
    }

    override fun findShoppingCartByUserId(id: String): Mono<ShoppingCart> {
        val query = Query()
            .addCriteria(Criteria.where("_id").`is`(id))
        return mongoTemplate.findOne(query, User::class.java)
            .map { it.shoppingCart }
    }

    override fun findById(id: String): Mono<User> {
        val query = Query()
            .addCriteria(Criteria.where("_id").`is`(id))
        return mongoTemplate.findOne(query, User::class.java)
    }

    override fun save(user: User): Mono<User> {
        return mongoTemplate.save(user)
    }

    override fun deleteAll(): Mono<DeleteResult> {
        val query = Query()
        return mongoTemplate.remove(query, User::class.java)
    }
}
