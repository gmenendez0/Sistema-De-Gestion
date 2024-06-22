package com.example.soporte.externalAPI;

import com.example.soporte.models.ExternalEntities.Client;

public class ClientRestAPIController extends RestAPIController{
    public ClientRestAPIController(){
        super("https://anypoint.mulesoft.com/mocking/api/v1/sources/exchange/assets/754f50e8-20d8-4223-bbdc-56d50131d0ae/clientes-psa/1.0.0/m/api/clientes");
    }

    @Override
    protected String apiName(){
        return "ClientAPI";
    }

    public Client[] getClients(){
        return getObject(Client[].class);
    }
}
