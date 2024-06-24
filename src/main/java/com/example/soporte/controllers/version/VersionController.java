package com.example.soporte.controllers.version;

import com.example.soporte.DTO.GetTicketDTO;
import com.example.soporte.controllers.Controller;
import com.example.soporte.models.Product.Version;
import com.example.soporte.services.service.VersionService;
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
public class VersionController extends Controller{
    private final VersionService versionService;

    @Autowired
    public VersionController(VersionService versionService) {
        super();
        this.versionService = versionService;
    }

    @GetMapping()
    public ResponseEntity<?> getVersions(){
        try {
            List<Version> versions = (List<Version>) versionService.getVersions();

            validateResource(versions);

            return okResponse(versions);
        } catch(Exception e){
            return handleError(e);
        }
    }

    @GetMapping("/{id}/tickets")
    @Deprecated
    public ResponseEntity<?> getVersionsTickets(@PathVariable Long id){
        try {
            Collection<GetTicketDTO> tickets = versionService.getTicketsByVersion(id);

            validateResource(tickets);

            return okResponse(tickets);
        } catch (Exception e){
            return handleError(e);
        }
    }
}
