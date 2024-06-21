package com.example.soporte.models.Ticket;

import com.example.soporte.DTO.TicketRequest;
import com.example.soporte.models.ExternalEntities.Task;
import com.example.soporte.models.Product.Version;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

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

    @Enumerated(EnumType.STRING)
    private Severity severity;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Long employeeId;
    private Long clientId;

    @ManyToOne
    @JoinColumn(name = "fk_version_id")
    @JsonManagedReference //
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
    public Ticket(TicketRequest ticketRequest) {
        this.title = ticketRequest.getTitle();
        this.description = ticketRequest.getDescription();
        this.severity = Severity.valueOf(ticketRequest.getSeverity().toUpperCase());
        this.status = Status.valueOf(ticketRequest.getStatus().toUpperCase());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Version getVersion() {
        return version;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
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

    public void update(TicketRequest ticketRequest) {
        this.title = ticketRequest.getTitle();
        this.description = ticketRequest.getDescription();
        this.status =  Status.valueOf(ticketRequest.getStatus().toUpperCase());
        this.severity = Severity.valueOf(ticketRequest.getSeverity().toUpperCase());
        this.clientId = ticketRequest.getClientId();
        this.employeeId = ticketRequest.getEmployeeId();

        // TODO actualizar lista de tareas
    }
}
