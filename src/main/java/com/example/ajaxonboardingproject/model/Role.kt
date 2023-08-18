package com.example.ajaxonboardingproject.model

import jakarta.persistence.*

@Entity
@Table(name = "roles")
data class Role(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id : Long = 0,
        @Column(unique = true) @Enumerated(value = EnumType.STRING) var name : RoleName? = null
) {
    enum class RoleName {
        ADMIN, USER
    }
}

