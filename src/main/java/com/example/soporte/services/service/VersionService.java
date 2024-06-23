package com.example.soporte.services.service;

import com.example.soporte.DTO.GetTicketDTO;
import com.example.soporte.models.Product.Version;
import com.example.soporte.models.Ticket.Ticket;
import com.example.soporte.repositories.VersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;
import java.util.Collection;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class VersionService extends Service<Version, Long>{
    private final TicketService ticketService;
    private final VersionRepository rrepository;

    @Lazy
    @Autowired
    public VersionService(VersionRepository repository, TicketService ticketService) {
        super(null);
        this.ticketService = ticketService;
        this.rrepository = repository;
    }

    public Collection<Version> getVersions() {
        return executeRepositorySupplierSafely(rrepository::findAll);
    }

    public Version getVersionById(Long versionId) {
        return executeRepositorySupplierSafely(() -> rrepository.findById(versionId).orElse(null));
    }

    public Collection<GetTicketDTO> getTicketsByVersion(Long versionId) {
        Collection<Ticket> tickets = ticketService.getTicketsByVersionId(versionId);
        return tickets.stream().map(ticketService::getTicketDTO).collect(Collectors.toList());
    }

    public Collection<GetTicketDTO> getTicketsByVersionWithPage(Long versionId, Pageable requestedPage) {
        Collection<Ticket> tickets = ticketService.getTicketPageByVersionId(versionId, requestedPage).getContent();
        return tickets.stream().map(ticketService::getTicketDTO).collect(Collectors.toList());
    }
}
