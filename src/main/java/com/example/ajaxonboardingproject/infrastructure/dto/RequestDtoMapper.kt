package com.example.ajaxonboardingproject.infrastructure.dto

interface RequestDtoMapper<D, T> {
    fun mapToModel(dto: D): T
}
