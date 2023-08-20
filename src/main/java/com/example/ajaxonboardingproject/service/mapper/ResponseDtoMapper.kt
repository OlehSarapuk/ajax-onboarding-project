package com.example.ajaxonboardingproject.service.mapper

interface ResponseDtoMapper<D, T> {
    fun mapToDto(model : T) : D
}