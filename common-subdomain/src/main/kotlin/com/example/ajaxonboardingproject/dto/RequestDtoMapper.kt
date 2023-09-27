package com.example.ajaxonboardingproject.dto

interface RequestDtoMapper<D, T> {
    fun mapToModel(dto: D): T
}
