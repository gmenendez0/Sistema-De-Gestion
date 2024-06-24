package com.example.soporte.externalAPI;

import com.example.soporte.exceptions.InterfaceException;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public abstract class RestAPIController {
    @SuppressWarnings("FieldCanBeLocal")
    private final int TIMEOUT = 999999;

    private final RestTemplate restTemplate;

    private final String url;

    public RestAPIController(String url) {
        this.restTemplate = new RestTemplate();
        this.url = url;
    }

    /**
     * Retrieves an object from the external API.
     *
     * @param aClass the class type of the object to retrieve
     * @param <T>    the type of the object to retrieve
     * @return the retrieved object
     * @throws InterfaceException if an error occurs while fetching data from the API
     */
    protected <T> T getObject(Class<T> aClass) throws InterfaceException {
        try {
            return restTemplate.getForObject(url, aClass);
        } catch (RestClientException e) {
            throw new InterfaceException("Error fetching data from external API " + apiName() + ": " + e.getMessage());
        }
    }

    /**
     * Updates an object on the external API using HTTP PATCH method.
     *
     * @param object the object to update
     * @param aClass the class type of the object
     * @param <T>    the type of the object
     * @throws InterfaceException if an error occurs while posting data to the API
     */
    protected <T> void patchObject(Object object, Class<T> aClass) throws InterfaceException {
        try {
            setPatchConfig();
            restTemplate.patchForObject(url, object, aClass);
        } catch (RestClientException e) {
            throw new InterfaceException("Error posting data to external API " + apiName() + ": " + e.getMessage());
        }
    }

    /**
     * Configures the HTTP PATCH request with timeout settings.
     */
    private void setPatchConfig(){
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(TIMEOUT);
        factory.setConnectionRequestTimeout(TIMEOUT);
        restTemplate.setRequestFactory(factory);
    }

    /**
     * Abstract method to be implemented by subclasses to provide the name or identifier of the API.
     *
     * @return the name or identifier of the API
     */
    protected abstract String apiName();
}
