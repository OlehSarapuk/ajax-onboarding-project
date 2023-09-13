package com.example.ajaxonboardingproject.service.impl

import com.example.ajaxonboardingproject.exception.AuthenticationException
import com.example.ajaxonboardingproject.model.Role
import com.example.ajaxonboardingproject.model.ShoppingCart
import com.example.ajaxonboardingproject.model.User
import com.example.ajaxonboardingproject.service.AuthenticationService
import com.example.ajaxonboardingproject.service.ShoppingCartService
import com.example.ajaxonboardingproject.service.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono

@Service
class AuthenticationServiceImpl(
    private val userService: UserService,
    private val shoppingCartService: ShoppingCartService,
    private val passwordEncoder: PasswordEncoder
) : AuthenticationService {
    @Transactional
    override fun register(
        email: String,
        password: String
    ): Mono<User> {
        val roles = mutableSetOf(Role.USER)
        lateinit var shoppingCart: ShoppingCart
        shoppingCartService.registerNewShoppingCart().map { shoppingCart = it }
        val user = User(
            email = email,
            password = password,
            roles = roles,
            shoppingCart = shoppingCart
        )
        return userService.add(user)
    }

    override fun login(
        login: String,
        password: String
    ): Mono<User> {
        val user: Mono<User> = userService.findByEmail(login)
        val encodedPassword: String = passwordEncoder.encode(password)
        return if (passwordEncoder.matches(password, encodedPassword)) user
        else throw AuthenticationException("Incorrect username or password!!!")
    }
}
