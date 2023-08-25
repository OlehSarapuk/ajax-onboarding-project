package com.example.ajaxonboardingproject.repository

import com.example.ajaxonboardingproject.model.Role
import org.springframework.data.mongodb.repository.MongoRepository

interface RoleRepository : MongoRepository<Role, Long> {
    fun findByName(name: Role.RoleName): Role?
}
