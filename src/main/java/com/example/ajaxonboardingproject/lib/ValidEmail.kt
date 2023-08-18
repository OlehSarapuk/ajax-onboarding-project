package com.example.ajaxonboardingproject.lib

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(validatedBy = [EmailValidator::class])
@Target(AnnotationTarget.TYPE)
@Retention(AnnotationRetention.RUNTIME)
annotation class ValidEmail(
        val message: String = "Invalid email",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<out Payload>> = [])
