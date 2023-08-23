package com.example.ajaxonboardingproject.model

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "roles")
data class Role(
        @Column(unique = true)
        @JsonProperty("name")
        @Enumerated(value = EnumType.STRING)
        var name : RoleName,
) {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        lateinit var id : java.lang.Long
        enum class RoleName {
            ADMIN, USER
        }
}

