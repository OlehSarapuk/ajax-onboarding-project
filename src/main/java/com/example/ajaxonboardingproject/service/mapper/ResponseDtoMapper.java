package com.example.ajaxonboardingproject.service.mapper;

public interface ResponseDtoMapper<D, T> {
    D mapToDto(T t);
}
