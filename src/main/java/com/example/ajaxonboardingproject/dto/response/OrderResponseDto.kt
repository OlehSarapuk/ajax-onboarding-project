package com.example.ajaxonboardingproject.dto.response

import java.time.LocalDateTime

data class OrderResponseDto(
        var id: java.lang.Long,
        var ticketIds: List<java.lang.Long>,
        var userId: java.lang.Long,
        var orderTime: LocalDateTime)
