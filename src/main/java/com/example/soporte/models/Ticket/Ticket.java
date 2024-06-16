package com.example.soporte.models.Ticket;

import com.example.soporte.models.ExternalEntities.Client;
import com.example.soporte.models.ExternalEntities.Employee;
import com.example.soporte.models.Product.Version;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    private String title;
    private String description;
    private Severity severity;
    private Status status;
    private LocalDateTime resolution_date_time;
    @ManyToOne
    @JoinColumn(name = "fk_employee_id")
    private Employee employee;
    @ManyToOne
    @JoinColumn(name = "fk_client_id")
    private Client client;
    @ManyToOne
    @JoinColumn(name = "fk_version_id")
    @JsonManagedReference
    private Version version;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
    public Ticket() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setVersion(Version version) {
        this.version = version;
    }
}
