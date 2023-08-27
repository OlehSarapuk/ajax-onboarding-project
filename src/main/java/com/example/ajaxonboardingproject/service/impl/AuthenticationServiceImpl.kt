package com.example.ajaxonboardingproject.service.impl

import com.example.ajaxonboardingproject.exception.AuthenticationException
import com.example.ajaxonboardingproject.model.Role
import com.example.ajaxonboardingproject.model.User
import com.example.ajaxonboardingproject.service.AuthenticationService
import com.example.ajaxonboardingproject.service.ShoppingCartService
import com.example.ajaxonboardingproject.service.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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
    ): User {
//        val roles = mutableSetOf(roleService.getByRoleName("USER"))
        val role = Role.USER
        val roles = mutableSetOf(role)
        val user = User(
            email = email,
            password = password,
            roles = roles,
            shoppingCart = shoppingCartService.registerNewShoppingCart()
        )
        userService.add(user)
        return user
    }

    override fun login(
        login: String,
        password: String
    ): User {
        val user: User = userService.findByEmail(login)
        val encodedPassword: String = passwordEncoder.encode(password)
        return if (passwordEncoder.matches(password, encodedPassword)) user
        else throw AuthenticationException("Incorrect username or password!!!")
    }
}
