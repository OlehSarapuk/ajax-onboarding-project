package com.example.ajaxonboardingproject.service.impl

import com.example.ajaxonboardingproject.model.User
import com.example.ajaxonboardingproject.repository.UserRepository
import com.example.ajaxonboardingproject.service.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UserServiceImpl(
    private val encoder: PasswordEncoder,
    private val userRepository: UserRepository
) : UserService {
    override fun add(user: User): Mono<User> {
        user.password = encoder.encode(user.password)
        return userRepository.save(user)
    }

    override fun get(id: String): Mono<User> {
        return userRepository.findById(id)
    }

    override fun findByEmail(email: String): Mono<User> {
        return userRepository.findByEmail(email)
    }

}
