package com.example.soporte.controllers;

import com.example.soporte.models.Ticket;
import com.example.soporte.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    private  TicketService ticketService;
    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Ticket createTicket(@RequestBody Ticket ticket) {return ticketService.createTicket(ticket);}

    @GetMapping
    public Collection<Ticket> getTickets(){
        return ticketService.getTickets();
    }

}
