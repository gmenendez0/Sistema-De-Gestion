package com.example.soporte.controllers;

import com.example.soporte.models.Product.Version;
import com.example.soporte.models.Ticket.Ticket;
import com.example.soporte.services.TicketService;
import com.example.soporte.services.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/v1/versions")
public class VersionController extends Controller {
    private final VersionService versionService;
    private final TicketService ticketService;

    @Autowired
    public VersionController(VersionService versionService, TicketService ticketService) {
        super();
        this.versionService = versionService;
        this.ticketService = ticketService;
    }

    @GetMapping()
    public ResponseEntity<?> getVersions(){
        try {
            Collection<Version> versions = versionService.getVersions();

            validateResource(versions);

            return okResponse(versions);
        } catch(Exception e){
            return handleError(e);
        }
    }

    @GetMapping("/{id}/tickets")
    public ResponseEntity<?> getVersionsTickets(@PathVariable Long id){
        try {
            Collection<Ticket> tickets = versionService.getTicketsByVersion(id);

            validateResource(tickets);

            return okResponse(tickets);
        } catch (Exception e){
            return handleError(e);
        }
    }




}
