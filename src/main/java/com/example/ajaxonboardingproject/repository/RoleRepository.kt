package com.example.ajaxonboardingproject.repository

import com.example.ajaxonboardingproject.model.Role
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface RoleRepository : JpaRepository<Role, Long>{
    fun findByName(name : Role.RoleName) : Optional<Role>
}