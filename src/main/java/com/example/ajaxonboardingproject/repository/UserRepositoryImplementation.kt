package com.example.ajaxonboardingproject.repository

import com.example.ajaxonboardingproject.model.ShoppingCart
import com.example.ajaxonboardingproject.model.User
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImplementation(
    private val mongoTemplate: MongoTemplate
): UserRepository {
    override fun findByEmail(email: String): User? {
        val query = Query()
            .addCriteria(Criteria.where("email").`is`(email))
        return mongoTemplate.findOne(query, User::class.java)
    }

    override fun findShoppingCartByUserId(id: String): ShoppingCart? {
        val query = Query()
            .addCriteria(Criteria.where("_id").`is`(id))
        return mongoTemplate.findOne(query, User::class.java)?.shoppingCart
    }

    override fun findById(id: String): User? {
        val query = Query()
            .addCriteria(Criteria.where("_id").`is`(id))
        return mongoTemplate.findOne(query, User::class.java)
    }

    override fun save(user: User): User {
        return mongoTemplate.save(user)
    }
}