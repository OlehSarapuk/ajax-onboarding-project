package com.example.ajaxonboardingproject.dto.response

import java.time.LocalDateTime

data class OrderResponseDto(
    var id: Long,
    var ticketIds: List<Long>,
    var userId: Long,
    var orderTime: LocalDateTime
)
