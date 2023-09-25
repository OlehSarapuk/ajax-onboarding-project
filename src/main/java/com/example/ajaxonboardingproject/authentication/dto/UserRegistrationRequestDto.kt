package com.example.ajaxonboardingproject.authentication.dto

import com.example.ajaxonboardingproject.authentication.lib.ValidEmail
import jakarta.validation.constraints.Size
import java.io.Serializable

@Suppress("SerialVersionUIDInSerializableClass")
data class UserRegistrationRequestDto(
    @field:ValidEmail
    val email: String,
    @field:Size(min = 8, max = 40)
    val password: String,
    val repeatPassword: String
): Serializable
