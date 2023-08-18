package com.example.ajaxonboardingproject.dto.request

import jakarta.validation.constraints.Size

data class MovieRequestDto(
        val title : String, // @NotNull
        val description : @Size(max = 200) String
)
