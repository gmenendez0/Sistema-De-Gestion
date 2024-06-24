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

    /**
     * Endpoint to create a new ticket.
     *
     * @param ticket the ticket data to create
     * @return a ResponseEntity with the created ticket data
     */
    @PostMapping
    public ResponseEntity<?> createTicket(@Valid @RequestBody CreateTicketDTO ticket) {
        try {
            GetTicketDTO savedTicket = ticketService.createTicket(ticket);
            return createdResponse(savedTicket);
        } catch (Exception e) {
            return handleError(e);
        }
    }

    /**
     * Endpoint to update an existing ticket.
     *
     * @param id the ID of the ticket to update
     * @param updateTicketDTO the updated ticket data
     * @return a ResponseEntity with the updated ticket data
     */
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateTicket(@PathVariable Long id, @Valid @RequestBody UpdateTicketDTO updateTicketDTO) {
        try {
            GetTicketDTO ticket = ticketService.updateTicket(updateTicketDTO, id);

            validateResource(ticket);

            return okResponse(ticket);
        } catch (Exception e) {
            return handleError(e);
        }
    }

    /**
     * Endpoint to retrieve a ticket by its ID.
     *
     * @param id the ID of the ticket to retrieve
     * @return a ResponseEntity with the ticket data
     */
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

    /**
     * Endpoint to retrieve all tickets.
     *
     * @return a ResponseEntity with a list of all tickets
     */
    @GetMapping
    public ResponseEntity<?> getTickets() {
        try {
            return okResponse(ticketService.getAllTicketsDTOs());
        } catch (Exception e) {
            return handleError(e);
        }
    }

    /**
     * Endpoint to delete a ticket by its ID.
     *
     * @param id the ID of the ticket to delete
     * @return a ResponseEntity indicating success, no matter if the ticket was found or not.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTicket(@PathVariable Long id) {
        try {
            ticketService.deleteTicketById(id);
            return noContentResponse();
        } catch (Exception e) {
            return handleError(e);
        }
    }

    /**
     * Endpoint to retrieve tasks associated with a ticket by its ID.
     *
     * @param id the ID of the ticket
     * @return a ResponseEntity with a list of task IDs
     */
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

    /**
     * Endpoint to retrieve the maximum response days for a ticket by its ID.
     *
     * @param id the ID of the ticket
     * @return a ResponseEntity with the maximum response days
     */
    @GetMapping("/{id}/max-response-days")
    public ResponseEntity<?> getTicketMaxResponseDays(@PathVariable Long id) {
        try {
            Duration responseTime = ticketService.getTicketMaxResponseTime(id);

            validateResource(responseTime);

            return okResponse(responseTime.toDays());
        } catch (Exception e) {
            return handleError(e);
        }
    }

    /**
     * Endpoint to retrieve statistics for a ticket by its ID.
     *
     * @param id the ID of the ticket
     * @return a ResponseEntity with ticket statistics
     */
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
