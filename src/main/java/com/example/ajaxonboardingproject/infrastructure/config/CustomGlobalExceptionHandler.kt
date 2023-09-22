package com.example.ajaxonboardingproject.infrastructure.config

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.server.ResponseStatusException

@ControllerAdvice
internal class CustomGlobalExceptionHandler {
    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun handleValidationException(
        ex: MethodArgumentNotValidException
    ): ResponseEntity<Map<String, String>> {
        val errors: Map<String, String> = ex.bindingResult.fieldErrors
            .associate { error ->
                error.field to (error.defaultMessage ?: "Validation error")
            }
        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [ResponseStatusException::class])
    fun handleMemoryException(
        ex: ResponseStatusException
    ): ResponseEntity<Map<String, String>> {
        val reason = ex.reason ?: "Unknown error"
        return ResponseEntity(mapOf("error" to reason), HttpStatus.BAD_REQUEST)
    }
}
