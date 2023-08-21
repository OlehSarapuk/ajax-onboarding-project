package com.example.ajaxonboardingproject.model

import jakarta.persistence.*

@Entity
@Table(name = "roles")
data class Role(
        @Column(unique = true)
        @Enumerated(value = EnumType.STRING)
        var name : RoleName,
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id : Long? = null
) {
    enum class RoleName {
        ADMIN, USER
    }
}

