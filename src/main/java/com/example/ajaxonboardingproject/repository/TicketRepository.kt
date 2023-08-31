package com.example.ajaxonboardingproject.repository

import com.example.ajaxonboardingproject.model.Ticket
import org.springframework.data.mongodb.repository.MongoRepository

interface TicketRepository : MongoRepository<Ticket, String>
