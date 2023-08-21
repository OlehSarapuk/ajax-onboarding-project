package com.example.ajaxonboardingproject.controller

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*

@ControllerAdvice
class CustomGlobalExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(value = [IllegalArgumentException::class, IllegalStateException::class])
    protected fun handleException(
            ex: MethodArgumentNotValidException,
            headers: HttpHeaders?,
            status: HttpStatus): ResponseEntity<Any> {
        val body: MutableMap<String, Any> = LinkedHashMap()
        body["timestamp"] = Date()
        body["status"] = status.value()
        val errors = ex.bindingResult
                .allErrors
                .stream()
                .map { obj: ObjectError -> obj.defaultMessage }
                .toList()
        body["errors"] = errors
        return ResponseEntity(body, headers, status)
    }
}