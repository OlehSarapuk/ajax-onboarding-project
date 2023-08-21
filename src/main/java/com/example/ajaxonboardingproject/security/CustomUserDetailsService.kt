package com.example.ajaxonboardingproject.security

import com.example.ajaxonboardingproject.model.User
import com.example.ajaxonboardingproject.service.UserService
import org.springframework.security.core.userdetails.User.UserBuilder
import org.springframework.security.core.userdetails.User.withUsername
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(private val userService: UserService) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user : User = userService.findByEmail(username)
                .orElseThrow{UsernameNotFoundException("Can't get user by email $username")}
        val builder : UserBuilder = withUsername(username)
        builder.password(user.password)
        builder.roles(*user.roles.map { it.name.toString() }.toTypedArray())
        return builder.build()
    }
}