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
public class VersionService extends Service{
    private final TicketService ticketService;
    private final VersionRepository repository;

    @Lazy
    @Autowired
    public VersionService(VersionRepository repository, TicketService ticketService) {
        this.ticketService = ticketService;
        this.repository = repository;
    }

    /**
     * Retrieves all versions.
     *
     * @return Collection of Version entities.
     */
    public Collection<Version> getVersions() {
        return executeRepositorySupplierSafely(repository::findAll);
    }

    /**
     * Retrieves a version by its ID.
     *
     * @param versionId ID of the version to retrieve.
     * @return Version entity if found, otherwise null.
     */
    public Version getVersionById(Long versionId) {
        return executeRepositorySupplierSafely(() -> repository.findById(versionId).orElse(null));
    }

    /**
     * Retrieves tickets associated with a specific version.
     *
     * @param versionId ID of the version for which tickets are to be retrieved.
     * @return Collection of GetTicketDTO representing tickets associated with the version.
     */
    public Collection<GetTicketDTO> getTicketsByVersion(Long versionId) {
        Collection<Ticket> tickets = ticketService.getTicketsByVersionId(versionId);
        return convertTicketsToGetTicketDTO(tickets);
    }

    /**
     * Retrieves paginated tickets associated with a specific version.
     *
     * @param versionId     ID of the version for which tickets are to be retrieved.
     * @param requestedPage Pageable object specifying the page details.
     * @return Collection of GetTicketDTO representing paginated tickets associated with the version.
     */
    public Collection<GetTicketDTO> getTicketsByVersionWithPage(Long versionId, Pageable requestedPage) {
        Collection<Ticket> tickets = ticketService.getTicketPageByVersionId(versionId, requestedPage).getContent();
        return convertTicketsToGetTicketDTO(tickets);
    }

    /**
     * Converts a collection of Ticket entities into a collection of GetTicketDTO objects.
     *
     * @param tickets Collection of Ticket entities to be converted.
     * @return Collection of GetTicketDTO representing the converted tickets.
     */
    private Collection<GetTicketDTO> convertTicketsToGetTicketDTO(Collection<Ticket> tickets) {
        return tickets.stream().map(this::convertTicketToGetTicketDTO).collect(Collectors.toList());
    }

    /**
     * Converts a Ticket entity into a GetTicketDTO object.
     *
     * @param ticket Ticket entity to be converted.
     * @return GetTicketDTO representing the converted ticket.
     */
    private GetTicketDTO convertTicketToGetTicketDTO(Ticket ticket) {
        return ticketService.getTicketDTO(ticket);
    }
}
