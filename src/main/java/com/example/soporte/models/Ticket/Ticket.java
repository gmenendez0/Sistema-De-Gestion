package com.example.soporte.models.Ticket;

import com.example.soporte.DTO.CreateTicketDTO;
import com.example.soporte.models.ExternalEntities.Task;
import com.example.soporte.models.Product.Version;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

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

    public Ticket(CreateTicketDTO createTicketDTO) {
        setTitle(createTicketDTO.title);
        setDescription(createTicketDTO.description);
        setSeverity(Severity.valueOf(createTicketDTO.severity.toUpperCase())); //BUG ACA SI SE PASA UNA SEVERIDAD QUE NO EXISTE COMO NULL
        setStatus(Status.valueOf(createTicketDTO.status.toUpperCase())); //BUG ACA SI SE PASA UNA SEVERIDAD QUE NO EXISTE COMO NULL
        setVersion(createTicketDTO.version);
        //this.client = createTicketDTO.client;
        //this.employee = createTicketDTO.employee;
        setTasks(createTicketDTO.tasks);
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
        if(this.version != null) this.version.removeTicket(this);
        this.version = version;
        version.addTicket(this);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Duration getMaxResponseTime() {
        if(severity == null) return Duration.of(0, ChronoUnit.DAYS);
        return severity.getMaxResolutionTime();
    }

    public void update(CreateTicketDTO createTicketDTO) {
        this.title = createTicketDTO.title;
        this.description = createTicketDTO.description;
        this.status =  Status.valueOf(createTicketDTO.status.toUpperCase());
        this.severity = Severity.valueOf(createTicketDTO.severity.toUpperCase());
        this.clientId = createTicketDTO.clientId;
        this.employeeId = createTicketDTO.employeeId;
        // TODO actualizar lista de tareas
    }
}
