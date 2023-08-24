package com.example.ajaxonboardingproject.repository

import com.example.ajaxonboardingproject.model.Ticket
import org.springframework.data.jpa.repository.JpaRepository

interface TicketRepository : JpaRepository<Ticket, Long>
