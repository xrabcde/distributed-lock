package com.example.distributedlock

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tickets")
class TicketController(
    private val ticketService: TicketService
) {
    private val logger = LoggerFactory.getLogger(Logger::class.java)

    @PostMapping
    fun create(): ResponseEntity<String> {
        val result = ticketService.createTicketWithSpinLock()
        logger.info(result)
        return ResponseEntity.ok(result)
    }
}
