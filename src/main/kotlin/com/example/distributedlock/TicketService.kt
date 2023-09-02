package com.example.distributedlock

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TicketService(
    private val ticketRepository: TicketRepository,
) {
    @Transactional
    fun createTicket() = when {
        ticketRepository.count() < 30 -> {
            val ticket = ticketRepository.save(Ticket())
            "SUCCESS! TICKET_NO : ${ticket.id}"
        }
        else -> "SOLD OUT"
    }
}
