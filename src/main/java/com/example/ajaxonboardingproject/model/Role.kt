package com.example.ajaxonboardingproject.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.mongodb.core.mapping.Document

@Document("roles")
data class Role(
    @JsonProperty("name")
    var name: RoleName,
) {
    enum class RoleName {
        ADMIN, USER
    }
}
