package com.example.soporte.models.Product;

import com.example.soporte.models.Ticket.Ticket;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "versions")
public class Version{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "fk_product_id")
    @JsonBackReference
    private Product product;

    @OneToMany(mappedBy = "version", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Ticket> tickets = new ArrayList<>();

    public Version(){}

    public Version(String name, Product product){
        this.name = name;
        this.product = product;
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
        ticket.setVersion(this);
    }

    public List<Ticket> getTickets() {
        return tickets;
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
