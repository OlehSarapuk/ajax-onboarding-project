package com.example.ajaxonboardingproject.infrastructure.dto

interface ResponseDtoMapper<D, T> {
    fun mapToDto(model: T): D
}
