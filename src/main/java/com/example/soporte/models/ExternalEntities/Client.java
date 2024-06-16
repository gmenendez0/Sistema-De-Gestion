package com.example.soporte.models.ExternalEntities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Client {
    @Id
    private long id;

    private String cuit;

    private String businessName;

    public Client(){}

    public Client(long id){
        this.id = id;
        this.cuit = null;
        this.businessName = null;
    }

    public Client(long id, String cuit, String businessName){
        this.id = id;
        this.cuit = cuit;
        this.businessName = businessName;
    }

    public long getID(){
        return id;
    }

    public String getCuit(){
        return cuit;
    }

    public String getBusinessName(){
        return businessName;
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
