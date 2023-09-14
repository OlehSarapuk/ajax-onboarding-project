package com.example.ajaxonboardingproject.dto.request

import jakarta.validation.constraints.NotBlank
import java.io.Serializable

@Suppress("SerialVersionUIDInSerializableClass")
data class UserLoginRequestDto(
    @field:NotBlank(message = "Login can't be null or blank!")
    val login: String,
    @field:NotBlank(message = "password can't be null or blank!")
    val password: String
) : Serializable
