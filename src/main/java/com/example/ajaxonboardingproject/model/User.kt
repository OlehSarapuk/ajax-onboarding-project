package com.example.ajaxonboardingproject.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("users")
data class User(
    var email: String,
    var password: String,
    var roles: MutableSet<Role>,
    var shoppingCart: ShoppingCart
) {
    @Id
    lateinit var id: String

    override fun toString(): String {
        return "User(id=$id, email=$email, roles=$roles)"
    }
}

enum class Role {
    ADMIN, USER
}
