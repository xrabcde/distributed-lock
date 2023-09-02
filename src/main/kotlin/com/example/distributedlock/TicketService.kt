package com.example.distributedlock

import org.redisson.api.RedissonClient
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TicketService(
    private val ticketRepository: TicketRepository,
    private val redisLockRepository: RedisLockRepository,
    private val redissonClient: RedissonClient
) {
    @Transactional
    fun createTicket() = when {
        ticketRepository.count() < 30 -> {
            val ticket = ticketRepository.save(Ticket())
            "SUCCESS! TICKET_NO : ${ticket.id}"
        }
        else -> "SOLD OUT"
    }

    fun createTicketWithSpinLock(): String {
        while (!redisLockRepository.lock("lock")!!) {
            Thread.sleep(1000)
        }

        return try {
            when {
                ticketRepository.count() < 30 -> {
                    val ticket = ticketRepository.save(Ticket())
                    "SUCCESS! TICKET_NO : ${ticket.id}"
                }
                else -> "SOLD OUT"
            }
        } finally {
            redisLockRepository.unlock("lock")
        }
    }
}
