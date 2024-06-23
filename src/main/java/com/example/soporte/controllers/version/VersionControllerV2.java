package com.example.soporte.controllers.version;

import com.example.soporte.DTO.GetTicketDTO;
import com.example.soporte.controllers.Controller;
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

    @GetMapping("/{id}/tickets/{page}")
    public ResponseEntity<?> getVersionsTickets(@PathVariable Long id, @PathVariable Pageable page){
        try {
            Collection<GetTicketDTO> tickets = versionService.getTicketsByVersionWithPage(id, page);

            validateResource(tickets);

            return okResponse(tickets);
        } catch (Exception e){
            return handleError(e);
        }
    }
}
