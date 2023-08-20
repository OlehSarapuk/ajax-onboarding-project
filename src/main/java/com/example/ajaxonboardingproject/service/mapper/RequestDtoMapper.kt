package com.example.ajaxonboardingproject.service.mapper

interface RequestDtoMapper<D, T> {
    fun mapToModel(dto : D) : T
}