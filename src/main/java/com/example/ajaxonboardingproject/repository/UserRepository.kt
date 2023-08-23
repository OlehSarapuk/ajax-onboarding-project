package com.example.ajaxonboardingproject.repository

import com.example.ajaxonboardingproject.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserRepository : JpaRepository<User, Long> {
    @Query("from User u join fetch u.roles where u.id = :id")
    fun findByUserId(id : Long) : User?

    @Query("from User u join fetch u.roles where u.email = :email")
    fun findByEmail(email : String) : User?
}
