package com.example.soporte.models.Product;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//@Entity
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class) //TODO Corregir
public class Product{

    private long id;

    private String name;

    //@OneToMany(mappedBy = "product")
    private final List<Version> versions = new ArrayList<>();

    public Product(){}

    public Product(String name){
        this.name = name;
    }

    public long getID(){
        return id;
    }

    public String getName(){
        return name;
    }

    public Collection<Version> getVersions(){
        return versions;
    }

    public void addVersion(Version version){
        versions.add(version);
    }
}
