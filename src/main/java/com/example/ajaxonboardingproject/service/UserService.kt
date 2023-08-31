package com.example.ajaxonboardingproject.service

import com.example.ajaxonboardingproject.model.User

interface UserService {
    fun add(user: User): User

    fun get(id: String): User

    fun findByEmail(email: String): User
}
