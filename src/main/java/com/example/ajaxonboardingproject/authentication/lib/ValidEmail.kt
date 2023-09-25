package com.example.ajaxonboardingproject.authentication.lib

<<<<<<<< HEAD:src/main/java/com/example/ajaxonboardingproject/infrastructure/lib/ValidEmail.kt
package com.example.ajaxonboardingproject.infrastructure.lib
========
package com.example.ajaxonboardingproject.authentication.lib
>>>>>>>> origin/ddd:src/main/java/com/example/ajaxonboardingproject/authentication/lib/ValidEmail.kt

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@MustBeDocumented
@Constraint(validatedBy = [EmailValidator::class])
@Retention(AnnotationRetention.RUNTIME)
annotation class ValidEmail(
    val message: String = "Invalid email",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
