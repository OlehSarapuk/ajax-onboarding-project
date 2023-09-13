package com.example.ajaxonboardingproject.repository

import com.example.ajaxonboardingproject.model.Ticket
import reactor.core.publisher.Mono

interface TicketRepository {
    fun save(ticket: Ticket): Mono<Ticket>
}
