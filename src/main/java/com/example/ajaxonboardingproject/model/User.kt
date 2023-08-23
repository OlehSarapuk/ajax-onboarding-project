package com.example.ajaxonboardingproject.model

import jakarta.persistence.*


@Entity
@Table(name = "users")
data class User(
        var email : String,
        var password : String, // Should be excluded from toString()
        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(name = "users_roles",
                joinColumns = [JoinColumn(name = "user_id")],
                inverseJoinColumns = [JoinColumn(name = "role_id")])
        var roles : MutableSet<Role>,
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id : Long? = null
) {
        override fun toString(): String {
                return "User(id=$id, email=$email, roles=$roles)"
        }
}
