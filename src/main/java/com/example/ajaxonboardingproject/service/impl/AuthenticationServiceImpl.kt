package com.example.ajaxonboardingproject.service.impl

import com.example.ajaxonboardingproject.exception.AuthenticationException
import com.example.ajaxonboardingproject.model.User
import com.example.ajaxonboardingproject.service.AuthenticationService
import com.example.ajaxonboardingproject.service.RoleService
import com.example.ajaxonboardingproject.service.ShoppingCartService
import com.example.ajaxonboardingproject.service.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthenticationServiceImpl(
        private val userService: UserService,
        private val roleService: RoleService,
        private val shoppingCartService: ShoppingCartService,
        private val passwordEncoder: PasswordEncoder) : AuthenticationService{
    override fun register(email: String, password: String): User {
        val user = User()
        user.email = email
        user.password = password
        user.roles = mutableSetOf(roleService.getByRoleName("USER"))
        userService.add(user)
        shoppingCartService.registerNewShoppingCart(user)
        return user
    }

    override fun login(login: String, password: String): User {
        val user = userService.findByEmail(login).orElseThrow {
            NoSuchElementException("Can't find user by login $login")
        }
        val encodedPassword = passwordEncoder.encode(password)
        return if (passwordEncoder.matches(password, encodedPassword)) user
                else throw AuthenticationException("Incorrect username or password!!!")
    }
}