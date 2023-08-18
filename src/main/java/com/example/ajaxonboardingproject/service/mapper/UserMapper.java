package com.example.ajaxonboardingproject.service.mapper;

import com.example.ajaxonboardingproject.dto.response.UserResponseDto;
import com.example.ajaxonboardingproject.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements ResponseDtoMapper<UserResponseDto, User> {
    @Override
    public UserResponseDto mapToDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getEmail());
    }
}
