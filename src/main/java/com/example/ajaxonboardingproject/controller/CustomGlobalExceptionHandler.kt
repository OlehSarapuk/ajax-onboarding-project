package com.example.ajaxonboardingproject.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
    class CustomGlobalExceptionHandler {
    @ExceptionHandler(value = [IllegalArgumentException::class, MethodArgumentNotValidException::class])
    protected fun handleException(
            ex: MethodArgumentNotValidException): ResponseEntity<Map<String, String>> {
        val errors : Map<String, String> = ex.bindingResult.fieldErrors
                .associate{ error ->
                    error.field to (error.defaultMessage ?: "Validation error")
                }
        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    }
}
