package com.example.soporte.models.ExternalEntities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

public class Client {
    private long id;
    @JsonProperty("CUIT")
    private String cuit;

    @Transient
    @JsonProperty("razon social")
    private String businessName;

    public Client(){}

    public Client(long id, String cuit, String businessName){
        this.id = id;
        this.cuit = cuit;
        this.businessName = businessName;
    }

    public long getId(){
        return id;
    }

    public void setBusinessName(String businessName){
        this.businessName = businessName;
    }

    public void setCuit(String cuit){
        this.cuit = cuit;
    }

    public void setID(long id){
        this.id = id;
    }
}
