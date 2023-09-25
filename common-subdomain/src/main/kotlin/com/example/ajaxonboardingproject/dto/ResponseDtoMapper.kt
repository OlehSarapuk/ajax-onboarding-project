package com.example.ajaxonboardingproject.dto

interface ResponseDtoMapper<D, T> {
    fun mapToDto(model: T): D
}
