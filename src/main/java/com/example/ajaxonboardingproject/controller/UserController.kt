package com.example.ajaxonboardingproject.controller

import com.example.ajaxonboardingproject.dto.response.UserResponseDto
import com.example.ajaxonboardingproject.model.User
import com.example.ajaxonboardingproject.service.UserService
import com.example.ajaxonboardingproject.service.mapper.UserMapper
import com.example.ajaxonboardingproject.service.mapper.mapToDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
data class UserController(
        private val userService: UserService,
        private val userMapper: UserMapper){
    @GetMapping("/by-email")
    fun finByEmail (@RequestParam email : String) : UserResponseDto {
        val user: User = userService.findByEmail(email)
        return userMapper.mapToDto(user)
    }
}
