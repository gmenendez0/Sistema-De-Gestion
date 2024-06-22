package com.example.soporte.services;

import com.example.soporte.externalAPI.ClientRestAPIController;
import com.example.soporte.models.ExternalEntities.Client;
import java.util.*;

@org.springframework.stereotype.Service
public class ClientService extends Service <Client,Long> {
    private final ClientRestAPIController clientRestAPIController;

    public ClientService() {
        super(null);
        this.clientRestAPIController = new ClientRestAPIController();
    }

    public List<Client> getClients() {
        Client[] clientArray = clientRestAPIController.getClients();
        return Optional.ofNullable(clientArray).map(Arrays::asList).orElse(null);
    }

    public Client getClientById(Long id) {
        List<Client> clients = getClients();
        return clients.stream().filter(client -> client.getId() == (id)).findFirst().orElse(null);
    }

    public boolean clientExists(Long id) {
        return getClientById(id) != null;
    }
}
