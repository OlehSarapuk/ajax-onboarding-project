package com.example.ajaxonboardingproject.model

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table

@Entity
@Table(name = "users")
data class User(
    var email: String,
    var password: String,
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "users_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    var roles: MutableSet<Role>,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    lateinit var id: java.lang.Long

    override fun toString(): String {
        return "User(id=$id, email=$email, roles=$roles)"
    }
}
