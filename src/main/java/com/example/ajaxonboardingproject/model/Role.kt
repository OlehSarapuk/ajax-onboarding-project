package com.example.ajaxonboardingproject.model

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*

@Entity
@Table(name = "roles")
data class Role(
        @Column(unique = true)
        @JsonProperty("name")
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

