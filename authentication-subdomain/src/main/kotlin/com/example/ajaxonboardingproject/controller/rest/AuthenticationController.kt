package com.example.ajaxonboardingproject.controller.rest

import com.example.ajaxonboardingproject.dto.ResponseDtoMapper
import com.example.ajaxonboardingproject.dto.UserLoginRequestDto
import com.example.ajaxonboardingproject.dto.UserRegistrationRequestDto
import com.example.ajaxonboardingproject.dto.UserResponseDto
import com.example.ajaxonboardingproject.model.User
import com.example.ajaxonboardingproject.security.jwt.JwtTokenProvider
import com.example.ajaxonboardingproject.service.AuthenticationService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class AuthenticationController(
    private val authenticationService: AuthenticationService,
    private val userDtoResponseMapper: ResponseDtoMapper<UserResponseDto, User>,
    private val jwtTokenProvider: JwtTokenProvider
) {
    @PostMapping("/register")
    fun register(
        @Valid @RequestBody requestDto: UserRegistrationRequestDto
    ): Mono<UserResponseDto> {
        return authenticationService
            .register(requestDto.email, requestDto.password)
            .map { userDtoResponseMapper.mapToDto(it) }
    }

    @PostMapping("/login")
    fun login(
        @Valid @RequestBody requestDto: UserLoginRequestDto
    ): Mono<ResponseEntity<Pair<String, String>>> {
        return authenticationService.login(requestDto.login, requestDto.password)
            .map { jwtTokenProvider.createToken(it.email, it.roles) }
            .map { ResponseEntity("token" to it, HttpStatus.OK) }
    }
}
