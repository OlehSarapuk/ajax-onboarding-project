package com.example.ajaxonboardingproject.repository

import com.example.ajaxonboardingproject.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface UserRepository : JpaRepository<User, Long> {
    @Query("from User u join fetch u.roles where u.id = :id")
    override fun findById(id : Long) : Optional<User>

    @Query("from User u join fetch u.roles where u.email = :email")
    fun findByEmail(email : String) : Optional<User>
}