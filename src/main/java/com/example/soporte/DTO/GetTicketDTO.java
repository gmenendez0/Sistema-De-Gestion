package com.example.soporte.DTO;

import com.example.soporte.models.ExternalEntities.Client;
import com.example.soporte.models.ExternalEntities.Employee;
import com.example.soporte.models.Ticket.Severity;
import com.example.soporte.models.Ticket.Status;
import com.example.soporte.models.Ticket.Ticket;

import java.util.List;

public class GetTicketDTO{
    public final Long id;
    public final String title;
    public final String description;
    public final Status status;
    public final Severity severity;
    public final Client client;
    public final Employee employee;
    public final String version;
    public final String maxResponseTime;
    public final List<Long> taskIds;

    public GetTicketDTO(Ticket ticket, Client client, Employee employee){
        this.id = ticket.getId();
        this.title = ticket.getTitle();
        this.description = ticket.getDescription();
        this.status = ticket.getStatus();
        this.severity = ticket.getSeverity();
        this.version = ticket.getVersion().getName();
        this.maxResponseTime = ticket.getMaxResponseTime().toString();
        this.taskIds = ticket.getTasks().stream().toList();
        this.client = client;
        this.employee = employee;
    }
}
