package com.example.soporte.externalAPI;

import com.example.soporte.exceptions.InterfaceException;
import com.example.soporte.models.ExternalEntities.Client;

public class ClientRestAPIController extends RestAPIController{
    public ClientRestAPIController(){
        super("https://anypoint.mulesoft.com/mocking/api/v1/sources/exchange/assets/754f50e8-20d8-4223-bbdc-56d50131d0ae/clientes-psa/1.0.0/m/api/clientes");
    }


    /**
     * @inheritedDoc
     */
    @Override
    protected String apiName(){
        return "ClientAPI";
    }

    /**
     * Retrieves an array of Client objects from the API.
     *
     * @return an array of Client objects retrieved from the API
     * @throws InterfaceException if an error occurs while fetching data from the API
     */
    public Client[] getClients() throws InterfaceException{
        return getObject(Client[].class);
    }
}
