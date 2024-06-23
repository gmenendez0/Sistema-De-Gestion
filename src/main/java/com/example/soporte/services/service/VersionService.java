package com.example.soporte.services.service;

import com.example.soporte.DTO.GetTicketDTO;
import com.example.soporte.models.Product.Version;
import com.example.soporte.models.Ticket.Ticket;
import com.example.soporte.repositories.VersionRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class VersionService extends Service<Version, Long>{
    private TicketService ticketService;

    public VersionService(VersionRepository repository, TicketService ticketService) {
        super(repository);
        this.ticketService = ticketService;
    }

    public Collection<Version> getVersions() {
        return executeRepositorySupplierSafely(() -> repository.findAll());
    }

    public Collection<GetTicketDTO> getTicketsByVersion(Long versionId) {
        Optional<Version> version = executeRepositorySupplierSafely(() -> repository.findById(versionId));
        Collection<Ticket> tickets = version.map(Version::getTickets).orElse(null);

        if(tickets == null) return new ArrayList<>();

        return tickets.stream().map(ticket -> ticketService.getTicketDTO(ticket)).collect(Collectors.toList());
    }

    public Version getVersionById(Long versionId) {
        return repository.findById(versionId).orElse(null);
    }
}
