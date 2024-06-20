package com.example.soporte.models.ExternalEntities;

import com.example.soporte.models.Ticket.Ticket;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Task{
    @Id
    private long id;

    public Task(){}
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    public Task(long id){
        this.id = id;
    }

    public Task(long id, String title){
        this.id = id;
    }

    public long getId(){
        return id;
    }
}
