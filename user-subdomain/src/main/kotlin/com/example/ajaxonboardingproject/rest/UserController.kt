package com.example.ajaxonboardingproject.rest

import com.example.ajaxonboardingproject.dto.ResponseDtoMapper
import com.example.ajaxonboardingproject.dto.UserResponseDto
import com.example.ajaxonboardingproject.service.UserService
import com.example.ajaxonboardingproject.model.User
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
