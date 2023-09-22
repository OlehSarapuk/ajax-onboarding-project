package com.example.ajaxonboardingproject.user.model

import com.example.ajaxonboardingproject.shoppingcart.model.ShoppingCart
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("users")
data class User(
    val email: String,
    var password: String,
    val roles: MutableSet<Role>,
    val shoppingCart: ShoppingCart
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
