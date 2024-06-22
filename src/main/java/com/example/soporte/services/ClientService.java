package com.example.soporte.services;

import com.example.soporte.exceptions.InterfaceException;
import com.example.soporte.models.ExternalEntities.Client;
import org.springframework.web.client.RestTemplate;
import java.util.*;

//TODO:
//1. Quitar la tabla de la entidad Client.
@org.springframework.stereotype.Service
public class ClientService extends Service <Client,Long> {
    private final RestTemplate restTemplate;

    private static final String CLIENT_API_URL = "https://anypoint.mulesoft.com/mocking/api/v1/sources/exchange/assets/754f50e8-20d8-4223-bbdc-56d50131d0ae/clientes-psa/1.0.0/m/api/clientes";

    public ClientService() {
        super(null);
        restTemplate = new RestTemplate();
    }

    public List<Client> getClients() {
        try {
            Client[] clientArray = restTemplate.getForObject(CLIENT_API_URL, Client[].class);

            return Optional.ofNullable(clientArray).map(Arrays::asList).orElse(null);
        } catch (Exception e) {
            throw new InterfaceException("Error fetching clients from external API: " + e.getMessage());
        }
    }

    public Client getClientById(Long id) {
        List<Client> clients = getClients();
        return clients.stream().filter(client -> client.getId() == (id)).findFirst().orElse(null);
    }
}
