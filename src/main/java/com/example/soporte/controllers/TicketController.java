package com.example.soporte.controllers;

import com.example.soporte.DTO.CreateTicketDTO;
import com.example.soporte.DTO.GetTicketDTO;
import com.example.soporte.DTO.GetTicketStatisticsDTO;
import com.example.soporte.DTO.UpdateTicketDTO;
import com.example.soporte.models.Ticket.Ticket;
import com.example.soporte.services.service.TicketService;
import jakarta.validation.Valid;
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
    public ResponseEntity<?> createTicket(@Valid @RequestBody CreateTicketDTO ticket) {
        try {
            Ticket savedTicket = ticketService.createTicket(ticket);
            return createdResponse(savedTicket);
        } catch (Exception e) {
            return handleError(e);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateTicket(@PathVariable Long id, @Valid @RequestBody UpdateTicketDTO updateTicketDTO) {
        try {
            Ticket ticket = ticketService.updateTicket(updateTicketDTO, id);

            validateResource(ticket);

            return okResponse(ticket);
        } catch (Exception e) {
            return handleError(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTicket(@PathVariable Long id) {
        try {
            GetTicketDTO ticket = ticketService.getTicketDTOById(id);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTicket(@PathVariable Long id) {
        try {
            ticketService.deleteTicketById(id);
            return noContentResponse();
        } catch (Exception e) {
            return handleError(e);
        }
    }

    //TODO
    @GetMapping("/{id}/tasks")
    public ResponseEntity<?> getTasksByTicketId(@PathVariable Long id) {
        try {
            List<Long> tasks = ticketService.getTasksByTicketId(id);

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

            return okResponse(responseTime.toDays());
        } catch (Exception e) {
            return handleError(e);
        }
    }

    @GetMapping("/{id}/statistics")
    public ResponseEntity<?> getStatisticsByTicketId(@PathVariable Long id) {
        try {
           GetTicketStatisticsDTO dto = ticketService.getStatisticsByTicketId(id);
            validateResource(dto);
            return okResponse(dto);
        } catch (Exception e) {
            return handleError(e);
        }
    }
}
