package com.example.ajaxonboardingproject.service

import com.example.ajaxonboardingproject.model.Role

interface RoleService {
    fun add(role : Role) : Role

    fun getByRoleName(roleName : String) : Role
}