package com.example.ajaxonboardingproject.service.mapper

import com.example.ajaxonboardingproject.dto.response.UserResponseDto
import com.example.ajaxonboardingproject.model.User
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
