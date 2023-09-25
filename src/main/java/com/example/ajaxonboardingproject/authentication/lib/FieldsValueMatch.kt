package com.example.ajaxonboardingproject.authentication.lib

<<<<<<<< HEAD:src/main/java/com/example/ajaxonboardingproject/infrastructure/lib/FieldsValueMatch.kt
package com.example.ajaxonboardingproject.infrastructure.lib
========
package com.example.ajaxonboardingproject.authentication.lib
>>>>>>>> origin/ddd:src/main/java/com/example/ajaxonboardingproject/authentication/lib/FieldsValueMatch.kt

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Constraint(validatedBy = [FieldsValueMatchValidator::class])
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class FieldsValueMatch(
    val message: String = "Fields values don't match!",
    val field: String,
    val fieldMatch: String,
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
