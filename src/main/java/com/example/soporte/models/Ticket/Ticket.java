package com.example.soporte.models.Ticket;

import com.example.soporte.models.ExternalEntities.Client;
import com.example.soporte.models.ExternalEntities.Employee;
import com.example.soporte.models.ExternalEntities.Task;
import com.example.soporte.models.Product.Version;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

//TODO Estadisticas de Ticket
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

    @ManyToOne
    @JoinColumn(name = "fk_employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "fk_client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "fk_version_id")
    @JsonBackReference // "BackReference" no se serializa
    private Version version;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Task> tasks = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime creationDateTime;

    @Column(name = "Assigned_date_time")
    private LocalDateTime assignedDateTime;

    @Column(name = "resolution_date_time")
    private LocalDateTime resolutionDateTime;

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

    public List<Task> getTasks() {
        return tasks;
    }

    public Duration getMaxResponseTime() {
        if(severity == null) return Duration.of(0, ChronoUnit.DAYS);

        return severity.getMaxResolutionTime();
    }
}
