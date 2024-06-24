package com.example.soporte.controllers.version;

import com.example.soporte.DTO.GetTicketDTO;
import com.example.soporte.controllers.Controller;
import com.example.soporte.models.Product.Version;
import com.example.soporte.services.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/v2/versions")
public class VersionControllerV2 extends Controller {
    private final VersionService versionService;

    @Autowired
    public VersionControllerV2(VersionService versionService) {
        super();
        this.versionService = versionService;
    }

    /**
     * Retrieves tickets associated with a specific version with pagination.
     *
     * @param id the ID of the version
     * @param page the pagination information
     * @return a ResponseEntity containing a collection of tickets for the specified version
     */
    @GetMapping("/{id}/tickets")
    public ResponseEntity<?> getVersionsTickets(@PathVariable Long id, Pageable page){
        try {
            Collection<GetTicketDTO> tickets = versionService.getTicketsByVersionWithPage(id, page);

            validateResource(tickets);

            return okResponse(tickets);
        } catch (Exception e){
            return handleError(e);
        }
    }

    /**
     * Retrieves a specific version by its ID.
     *
     * @param id the ID of the version
     * @return a ResponseEntity containing the version information
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getVersion(@PathVariable Long id){
        try {
            Version version = versionService.getVersionById(id);

            validateResource(version);

            return okResponse(version);
        } catch (Exception e){
            return handleError(e);
        }
    }
}
