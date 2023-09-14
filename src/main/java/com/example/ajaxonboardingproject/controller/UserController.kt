package com.example.ajaxonboardingproject.controller

import com.example.ajaxonboardingproject.dto.response.UserResponseDto
import com.example.ajaxonboardingproject.model.User
import com.example.ajaxonboardingproject.service.UserService
import com.example.ajaxonboardingproject.service.mapper.ResponseDtoMapper
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
data class UserController(
    private val userService: UserService,
    private val userResponseDtoMapper: ResponseDtoMapper<UserResponseDto, User>
) {
    @GetMapping("/by-email")
    fun finByEmail(@RequestParam email: String): UserResponseDto {
        return userService.findByEmail(email)
            .map { userResponseDtoMapper.mapToDto(it) }
            .block()!!
    }
}
