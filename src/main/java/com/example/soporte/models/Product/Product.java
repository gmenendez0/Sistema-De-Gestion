package com.example.soporte.models.Product;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "products")
public class Product{

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonManagedReference
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
