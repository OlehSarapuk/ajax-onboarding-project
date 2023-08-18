package com.example.ajaxonboardingproject.dto.request

import com.example.ajaxonboardingproject.lib.FieldsValueMatch
import com.example.ajaxonboardingproject.lib.ValidEmail
import jakarta.validation.constraints.Size

@FieldsValueMatch(
        field = "password",
        fieldMatch = "repeatPassword",
        message = "Password do not match!"
)
data class UserRegistrationRequestDto(
        val email : @ValidEmail String,
        val password : @Size(min = 8, max = 40) String,
        val repeatPassword : String)