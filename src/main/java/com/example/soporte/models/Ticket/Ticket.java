package com.example.soporte.models.Ticket;

import com.example.soporte.DTO.CreateTicketDTO;
import com.example.soporte.models.Product.Version;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

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

    @Column(nullable = true)
    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private Severity severity;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = true)
    private Long employeeId;

    private Long clientId;

    @ManyToOne
    @JoinColumn(name = "fk_version_id")
    @JsonManagedReference //
    private Version version;

    @ElementCollection
    private List<Long> tasks = new ArrayList<>();

    @JsonIgnore
    private LocalDateTime creationDateTime;

    @JsonIgnore
     @Column(name = "Assigned_date_time")
     private LocalDateTime assignedDateTime;

    @JsonIgnore
    @Column(name = "resolution_date_time")
    private LocalDateTime resolutionDateTime;

    public Ticket() {
    }

    public Ticket(CreateTicketDTO createTicketDTO) {
        setTitle(createTicketDTO.title);
        setDescription(createTicketDTO.description);
        setSeverity(Severity.fromString(createTicketDTO.severity));
        setStatus(Status.fromString(createTicketDTO.status));
        setVersion(createTicketDTO.version);
        setClientId(createTicketDTO.clientId);
        setEmployeeId(createTicketDTO.employeeId);
        setTasks(createTicketDTO.tasksIds);
        setCreationDateTime(LocalDateTime.now());
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Severity getSeverity() {
        return severity;
    }

    public Status getStatus() {
        return status;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public Long getClientId() {
        return clientId;
    }

    public Version getVersion() {
        return version;
    }

    public Long getId() {
        return id;
    }

    public List<Long> getTasks() {
        return tasks;
    }

    public Duration getMaxResponseTime() {
        if(severity == null) return Duration.of(0, ChronoUnit.DAYS);
        return severity.getMaxResolutionTime();
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public LocalDateTime getAssignedDateTime() {
        return assignedDateTime;
    }

    public LocalDateTime getResolutionDateTime() {
        return resolutionDateTime;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public void setStatus(Status status) {
        if(status == Status.CERRADO) setResolutionDateTime(LocalDateTime.now());
        this.status = status;
    }

    public void setEmployeeId(Long employeeId) {
        if(employeeId == null) setAssignedDateTime(LocalDateTime.now());
        this.employeeId = employeeId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public void setTasks(List<Long> tasks) {
        this.tasks = tasks;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setVersion(Version version) {
        if(this.version != null) this.version.removeTicket(this);
        this.version = version;
        version.addTicket(this);
    }

    public void addTasks(List<Long> tasksId) {
        for (long taskId : tasksId) {
            if (!tasks.contains(taskId)) addTask(taskId);
        }
    }

    public void removeTasks(List<Long> tasks) {
        tasks.forEach(this::removeTask);
    }

    private void addTask(Long task) {
        tasks.add(task);
    }

    private void removeTask(Long task) {
        tasks.remove(task);
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public void setAssignedDateTime(LocalDateTime assignedDateTime) {
        this.assignedDateTime = assignedDateTime;
    }

    public void setResolutionDateTime(LocalDateTime resolutionDateTime) {
        this.resolutionDateTime = resolutionDateTime;
    }
}
