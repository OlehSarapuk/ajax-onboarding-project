package com.example.ajaxonboardingproject.repository

import com.example.ajaxonboardingproject.model.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email : String) : Optional<User>
}