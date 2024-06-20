package com.example.soporte.controllers;

import com.example.soporte.models.ExternalEntities.Client;
import com.example.soporte.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/clients")
public class ClientController extends Controller{
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<?> getClients(){
        try {
            return okResponse(clientService.getClients());
        } catch (Exception e) {
            return handleError(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClientById(@PathVariable long id){
        try {
            Client client = clientService.getClientById(id);

            validateResource(client);

            return okResponse(client);
        } catch (Exception e) {
            return handleError(e);
        }
    }
}
