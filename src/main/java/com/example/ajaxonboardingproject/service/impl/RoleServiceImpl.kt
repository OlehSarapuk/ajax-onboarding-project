package com.example.ajaxonboardingproject.service.impl

import com.example.ajaxonboardingproject.model.Role
import com.example.ajaxonboardingproject.repository.RoleRepository
import com.example.ajaxonboardingproject.service.RoleService
import org.springframework.stereotype.Service
import java.util.NoSuchElementException

@Service
class RoleServiceImpl (private val roleRepository: RoleRepository) : RoleService {
    override fun add(role : Role) : Role {
        return roleRepository.save(role)
    }

    override fun getByRoleName(roleName: String): Role {
        return roleRepository.findByName(Role.RoleName.valueOf(roleName))
                ?: throw NoSuchElementException("Can't ger role with name $roleName")
    }
}
