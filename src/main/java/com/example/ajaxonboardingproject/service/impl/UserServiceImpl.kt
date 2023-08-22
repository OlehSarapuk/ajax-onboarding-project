package com.example.ajaxonboardingproject.service.impl

import com.example.ajaxonboardingproject.model.User
import com.example.ajaxonboardingproject.repository.UserRepository
import com.example.ajaxonboardingproject.service.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import kotlin.NoSuchElementException

@Service
class UserServiceImpl(
        private val encoder : PasswordEncoder,
        private val userRepository: UserRepository) : UserService{
    override fun add(user: User): User {
        user.password = encoder.encode(user.password)
        return userRepository.save(user)
    }

    override fun get(id: Long): User {
        return userRepository.findByUserId(id) ?: throw NoSuchElementException("User with id $id not found")
    }

    override fun findByEmail(email: String): User {
        return userRepository.findByEmail(email) ?: throw NoSuchElementException("User with email $email not found")
    }

}
