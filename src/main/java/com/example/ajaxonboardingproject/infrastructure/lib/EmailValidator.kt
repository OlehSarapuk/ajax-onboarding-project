package com.example.ajaxonboardingproject.infrastructure.lib

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

val EMAIL_VALIDATION_REGEX = Regex("^(.+)@(.+)$")

class EmailValidator : ConstraintValidator<ValidEmail, String> {
    override fun isValid(
        field: String?,
        context: ConstraintValidatorContext
    ): Boolean = field?.matches(EMAIL_VALIDATION_REGEX) ?: false
}
