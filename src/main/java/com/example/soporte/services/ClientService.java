package com.example.soporte.services;

import com.example.soporte.models.ExternalEntities.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@org.springframework.stereotype.Service
public class ClientService extends Service <Client,Long> {
    @Autowired
    private RestTemplate restTemplate;
    public ClientService(JpaRepository<Client, Long> repository) {
        super(repository);
    }
    private static final String API_URL = "https://anypoint.mulesoft.com/mocking/api/v1/sources/exchange/assets/754f50e8-20d8-4223-bbdc-56d50131d0ae/clientes-psa/1.0.0/m/api/clientes";

    public List<Client> getClients() {
        Client[] clientesArray = restTemplate.getForObject(API_URL, Client[].class);
        return Arrays.asList(clientesArray);
    }
}
