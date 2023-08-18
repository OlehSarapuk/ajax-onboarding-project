package com.example.ajaxonboardingproject.lib

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.beans.BeanWrapperImpl

class FieldsValueMatchValidator(private val field : String, private val fieldMatch : String)
    : ConstraintValidator<FieldsValueMatch, Any> {
    override fun isValid(value : Any?, context : ConstraintValidatorContext?): Boolean {
        val fieldValue = value?.let { BeanWrapperImpl(value).getPropertyValue(field) }
        val fieldMatchValue = value?.let { BeanWrapperImpl(value).getPropertyValue(fieldMatch) }
        return fieldValue == fieldMatchValue
    }
}