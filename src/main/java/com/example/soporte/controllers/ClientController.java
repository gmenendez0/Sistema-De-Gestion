package com.example.soporte.controllers;


import com.example.soporte.models.ExternalEntities.Client;
import com.example.soporte.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/Client")
public class ClientController extends Controller{

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<?> getClients(){
        try {
            Collection<Client> clients = clientService.getClients();
            return okResponse(clients);
        } catch (Exception e) {
            return handleError(e);
        }
    }
}
