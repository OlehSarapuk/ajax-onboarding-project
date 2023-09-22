package com.example.ajaxonboardingproject.user.service.mapper

import com.example.ajaxonboardingproject.infrastructure.dto.ResponseDtoMapper
import com.example.ajaxonboardingproject.user.dto.UserResponseDto
import com.example.ajaxonboardingproject.user.model.User
import org.springframework.stereotype.Component

@Component
class UserMapper : ResponseDtoMapper<UserResponseDto, User> {
    override fun mapToDto(model: User): UserResponseDto {
        return UserResponseDto(
            id = model.id,
            email = model.email
        )
    }
}
