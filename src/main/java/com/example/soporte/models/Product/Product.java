package com.example.soporte.models.Product;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    public long getId() {
        return id;
    }

    public List<Version> getVersions() {
        return versions;
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonBackReference
    private  List<Version> versions = new ArrayList<>();

    public Product(){}

    public Product(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void addVersion(Version version){
        versions.add(version);
    }
}
