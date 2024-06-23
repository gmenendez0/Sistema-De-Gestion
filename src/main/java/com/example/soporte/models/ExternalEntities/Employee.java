package com.example.soporte.models.ExternalEntities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Employee{

    @JsonProperty("legajo")
    private long fileName;

    @JsonProperty("Nombre")
    private String name;

    @JsonProperty("Apellido")
    private String lastName;

    public Employee(){
    }

    public Employee(int fileName, String name, String lastName){
        this.fileName = fileName;
        this.name = name;
        this.lastName = lastName;
    }

    public long getFileName(){
        return (int) fileName;
    }

    public String getName(){
        return name;
    }

    public void setFileName(int fileName){
        this.fileName = fileName;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }
}
