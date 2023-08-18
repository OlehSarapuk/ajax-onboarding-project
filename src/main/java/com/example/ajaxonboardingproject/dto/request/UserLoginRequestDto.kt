package com.example.ajaxonboardingproject.dto.request

import jakarta.validation.constraints.NotBlank

data class UserLoginRequestDto(
        val login : @NotBlank(message = "Login can't be null or blank!") String,
        val password : @NotBlank(message = "password can't be null or blank!") String,
)
