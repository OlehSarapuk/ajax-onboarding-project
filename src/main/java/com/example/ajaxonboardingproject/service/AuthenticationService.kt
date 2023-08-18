package com.example.ajaxonboardingproject.service

import com.example.ajaxonboardingproject.model.User

interface AuthenticationService {
    fun register(email : String, password : String) : User

    fun login(login : String, password: String) : User
}