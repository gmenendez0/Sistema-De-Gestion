package com.example.soporte.models.ExternalEntities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Employee{
@Id
    private long fileName;

    private String name;

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
