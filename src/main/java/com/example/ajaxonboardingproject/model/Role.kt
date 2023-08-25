package com.example.ajaxonboardingproject.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("roles")
data class Role(
//    @Column(unique = true)
    @JsonProperty("name")
//    @Enumerated(value = EnumType.STRING)
    var name: RoleName,
) {

    enum class RoleName {
        ADMIN, USER
    }
}

