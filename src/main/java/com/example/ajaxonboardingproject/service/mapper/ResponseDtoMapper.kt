package com.example.ajaxonboardingproject.service.mapper

interface ResponseDtoMapper<D, T> {
    fun mapToDto(t : T) : D
}