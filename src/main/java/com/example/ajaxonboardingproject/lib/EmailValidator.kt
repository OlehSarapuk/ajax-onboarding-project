package com.example.ajaxonboardingproject.lib

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

const val EMAIL_VALIDATION_REGEX : String = "^(.+)@(.+)$"

class EmailValidator : ConstraintValidator<ValidEmail, String> {
    override fun isValid(field : String?, context : ConstraintValidatorContext) : Boolean =
            field?.matches(Regex(EMAIL_VALIDATION_REGEX)) ?: false
}
