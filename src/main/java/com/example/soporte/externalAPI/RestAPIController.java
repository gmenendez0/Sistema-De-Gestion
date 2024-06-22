package com.example.soporte.externalAPI;

import com.example.soporte.exceptions.InterfaceException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


public abstract class RestAPIController {
    private final RestTemplate restTemplate;

    private final String url;

    public RestAPIController(String url) {
        this.restTemplate = new RestTemplate();
        this.url = url;
    }

    protected <T> T getObject(Class<T> aClass) throws InterfaceException {
        try {
            return restTemplate.getForObject(url, aClass);
        } catch (RestClientException e) {
            throw new InterfaceException("Error fetching data from external API " + apiName() + ": " + e.getMessage());
        }
    }

    protected abstract String apiName();
}
