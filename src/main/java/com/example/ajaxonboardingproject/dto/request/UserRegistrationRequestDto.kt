package com.example.ajaxonboardingproject.dto.request

import com.example.ajaxonboardingproject.lib.ValidEmail
import jakarta.validation.constraints.Size

data class UserRegistrationRequestDto(
    @field:ValidEmail()
    val email: String,
    @field:Size(min = 8, max = 40)
    val password: String,
    val repeatPassword: String
)
