package com.example.soporte.models.Product;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

//@Entity
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class) //TODO Corregir
//TODO Corregir tema columns
public class Version{

    private long id;

    private String name;

    //@ManyToOne
    private Product product;

    public Version(){}

    public Version(String name, Product product){
        this.name = name;
        this.product = product;
    }

    public long getID(){
        return id;
    }

    public String getName(){
        return name;
    }

    public Product getProduct(){
        return product;
    }
}
