package com.example.ajaxonboardingproject.authentication.lib

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.beans.BeanWrapperImpl

class FieldsValueMatchValidator(private val field: String, private val fieldMatch: String) :
    ConstraintValidator<FieldsValueMatch, Any> {
    override fun isValid(
        value: Any?,
        context: ConstraintValidatorContext?
    ): Boolean {
        fun Any?.getPropertyValueOfNullable(field: String): Any? {
            return this?.let { BeanWrapperImpl(this).getPropertyValue(field) }
        }

        val fieldValue = value.getPropertyValueOfNullable(field)
        val fieldMatchValue = value.getPropertyValueOfNullable(fieldMatch)
        return fieldValue == fieldMatchValue
    }
}
