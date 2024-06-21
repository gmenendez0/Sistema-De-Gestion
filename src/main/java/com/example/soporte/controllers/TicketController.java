package com.example.soporte.controllers;

import com.example.soporte.DTO.TicketRequest;
import com.example.soporte.models.ExternalEntities.Task;
import com.example.soporte.models.Ticket.Ticket;
import com.example.soporte.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/v1/tickets")
public class TicketController extends Controller {
    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        super();
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<?> createTicket(
                                          @RequestBody TicketRequest ticket) {
        try {
            Ticket savedTicket = ticketService.createTicket(ticket);
            return createdResponse(savedTicket);
        } catch (Exception e) {
            return handleError(e);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTicket(@PathVariable Long id, @RequestBody TicketRequest ticketRequest) {
        try {
            Ticket ticket = ticketService.updateTicket(ticketRequest);
            validateResource(ticket);
            return okResponse(ticket);
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

    //TODO: Endpoint de modificar un ticket X

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTicket(@PathVariable Long id) {
        try {
            ticketService.deleteTicketById(id);
            return noContentResponse();
        } catch (Exception e) {
            return handleError(e);
        }
    }

    @GetMapping("/{id}/tasks")
    public ResponseEntity<?> getTasksByTicketId(@PathVariable Long id) {
        try {
            List<Task> tasks = ticketService.getTasksByTicketId(id);

            validateResource(tasks);

            return okResponse(tasks);
        } catch (Exception e) {
            return handleError(e);
        }
    }

    @GetMapping("/{id}/max-response-time")
    public ResponseEntity<?> getTicketMaxResponseTime(@PathVariable Long id) {
        try {
            Duration responseTime = ticketService.getTicketMaxResponseTime(id);

            validateResource(responseTime);

            return okResponse(responseTime);
        } catch (Exception e) {
            return handleError(e);
        }
    }
}
