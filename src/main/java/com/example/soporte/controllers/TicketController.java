package com.example.soporte.controllers;

import com.example.soporte.models.Ticket.Ticket;
import com.example.soporte.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/tickets")
public class TicketController extends Controller{
    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<?> createTicket(@RequestBody Ticket ticket) {
        try {
            Ticket savedTicket = ticketService.saveTicket(ticket);
            return createdResponse(savedTicket);
        } catch (Exception e) {
            return handleError(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTicket(@PathVariable Long id) {
        try {
            Ticket ticket = ticketService.getTicketById(id);

            validateResource(ticket);
            return okResponse(ticket);
        } catch (Exception e) {
            return handleError(e);
        }
    }

    @GetMapping
    public ResponseEntity<?> getTickets() {
        try {
            return okResponse(ticketService.getAllTickets());
        } catch (Exception e) {
            return handleError(e);
        }
    }

    //Modificar un ticket X

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTicket(@PathVariable Long id) {
        try {
            ticketService.deleteTicketById(id);
            return noContentResponse();
        } catch (Exception e) {
            return handleError(e);
        }
    }
}
