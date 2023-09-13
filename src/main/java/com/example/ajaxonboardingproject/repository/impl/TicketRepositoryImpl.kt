package com.example.ajaxonboardingproject.repository.impl

import com.example.ajaxonboardingproject.model.Ticket
import com.example.ajaxonboardingproject.repository.TicketRepository
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class TicketRepositoryImpl(
    private val mongoTemplate: ReactiveMongoTemplate
): TicketRepository {
    override fun save(ticket: Ticket): Mono<Ticket> {
        return mongoTemplate.save(ticket)
    }
}