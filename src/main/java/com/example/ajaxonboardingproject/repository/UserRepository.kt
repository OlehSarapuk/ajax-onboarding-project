package com.example.ajaxonboardingproject.repository

import com.example.ajaxonboardingproject.model.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository : MongoRepository<User, Long> {
//    @Query("from User u join fetch u.roles where u.id = :id")
    fun findById(id: String): User?

//    @Query("from User u join fetch u.roles where u.email = :email")
    fun findByEmail(email: String): User?
}
