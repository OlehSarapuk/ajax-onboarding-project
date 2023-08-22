package com.example.ajaxonboardingproject.controller

import com.example.ajaxonboardingproject.dto.request.UserLoginRequestDto
import com.example.ajaxonboardingproject.dto.request.UserRegistrationRequestDto
import com.example.ajaxonboardingproject.dto.response.UserResponseDto
import com.example.ajaxonboardingproject.security.jwt.JwtTokenProvider
import com.example.ajaxonboardingproject.service.AuthenticationService
import com.example.ajaxonboardingproject.service.mapper.UserMapper
import com.example.ajaxonboardingproject.service.mapper.mapToDto
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthenticationController(
        private val authenticationService : AuthenticationService,
        private val userMapper: UserMapper,
        private val jwtTokenProvider : JwtTokenProvider) {
    @PostMapping("/register")
    fun register(@Valid @RequestBody requestDto : UserRegistrationRequestDto) : UserResponseDto {
        val user = authenticationService.register(requestDto.email, requestDto.password)
        return userMapper.mapToDto(user)
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody requestDto : UserLoginRequestDto) : ResponseEntity<Any> {
        val user = authenticationService.login(requestDto.login, requestDto.password)
        val token = jwtTokenProvider.createToken(user.email, user.roles)
        return ResponseEntity("token" to token, HttpStatus.OK)
    }
}
