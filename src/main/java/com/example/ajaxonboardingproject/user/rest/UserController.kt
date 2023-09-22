package com.example.ajaxonboardingproject.user.rest

import com.example.ajaxonboardingproject.infrastructure.dto.ResponseDtoMapper
import com.example.ajaxonboardingproject.user.dto.UserResponseDto
import com.example.ajaxonboardingproject.user.service.UserService
import com.example.ajaxonboardingproject.user.model.User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/users")
data class UserController(
    private val userService: UserService,
    private val userResponseDtoMapper: ResponseDtoMapper<UserResponseDto, User>
) {
    @GetMapping("/by-email")
    fun finByEmail(@RequestParam email: String): Mono<UserResponseDto> {
        return userService.findByEmail(email)
            .map { userResponseDtoMapper.mapToDto(it) }
    }
}
