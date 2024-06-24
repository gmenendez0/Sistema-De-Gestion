package com.example.soporte.services.service;

import com.example.soporte.externalAPI.ClientRestAPIController;
import com.example.soporte.models.ExternalEntities.Client;

import java.util.*;

@org.springframework.stereotype.Service
public class ClientService extends Service{
    private final ClientRestAPIController clientRestAPIController;

    public ClientService() {
        this.clientRestAPIController = new ClientRestAPIController();
    }

    /**
     * Retrieves a list of all clients from the external API.
     *
     * @return a list of Client objects retrieved from the external API, or null if no clients are found
     */
    public List<Client> getClients() {
        Client[] clientArray = clientRestAPIController.getClients();
        return Optional.ofNullable(clientArray).map(Arrays::asList).orElse(null);
    }

    /**
     * Retrieves a client by their ID.
     *
     * @param id the ID of the client to retrieve
     * @return the Client object with the specified ID, or null if the client is not found
     */
    public Client getClientById(Long id) {
        List<Client> clients = getClients();
        return clients.stream().filter(client -> client.getId() == (id)).findFirst().orElse(null);
    }

    /**
     * Checks if a client with the specified ID exists.
     *
     * @param id the ID of the client to check
     * @return true if a client with the specified ID exists, false otherwise
     */
    public boolean clientExists(Long id) {
        return getClientById(id) != null;
    }
}
