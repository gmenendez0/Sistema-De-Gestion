package com.example.soporte.DTO;
import com.example.soporte.models.Ticket.Ticket;


import java.time.LocalDateTime;


public class GetTicketStatisticsDTO {
    public final Long id;

    public LocalDateTime creationDateTime;

    public LocalDateTime assignedDateTime;

    public LocalDateTime resolutionDateTime;

    public Long timeToAssignment;
    public Long timeToResolution;

    public Long totalTimeToResolution;
    public GetTicketStatisticsDTO(Ticket ticket){
        this.id = ticket.getId();
        this.creationDateTime = ticket.getCreationDateTime();
        this.assignedDateTime = ticket.getAssignedDateTime();
        this.resolutionDateTime = ticket.getResolutionDateTime();
    }
}
