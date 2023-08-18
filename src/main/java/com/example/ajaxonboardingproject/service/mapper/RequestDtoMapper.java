package com.example.ajaxonboardingproject.service.mapper;

public interface RequestDtoMapper<D, T> {
    T mapToModel(D dto);
}
