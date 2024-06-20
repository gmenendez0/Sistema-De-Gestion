package com.example.soporte.models.ExternalEntities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity
public class Employee{
    @Id
    @JsonProperty("legajo")
    private long fileName;

    @Transient
    @JsonProperty("Nombre")
    private String name;

    @Transient
    @JsonProperty("Apellido")
    private String lastName;

    public Employee(){
    }

    public Employee(long fileName){
        this.fileName = fileName;
        this.name = null;
        this.lastName = null;
    }

    public Employee(int fileName, String name, String lastName){
        this.fileName = fileName;
        this.name = name;
        this.lastName = lastName;
    }

    public int getFileName(){
        return (int) fileName;
    }

    public String getName(){
        return name;
    }

    public String getLastName(){
        return lastName;
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
