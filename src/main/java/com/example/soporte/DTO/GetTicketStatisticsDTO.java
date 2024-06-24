package com.example.soporte.DTO;
import com.example.soporte.models.Ticket.Ticket;

import java.time.Duration;
import java.time.LocalDateTime;

public class GetTicketStatisticsDTO {
    public final Long id;

    public LocalDateTime creationDateTime;

    public LocalDateTime assignedDateTime;

    public LocalDateTime resolutionDateTime;

    public Long hoursUntilAssigned;

    public Long hoursFromAssignedToSolved;

    public Long hoursUntilSolved;

    public GetTicketStatisticsDTO(Ticket ticket){
        this.id = ticket.getId();
        this.creationDateTime = ticket.getCreationDateTime();
        this.assignedDateTime = ticket.getAssignedDateTime();
        this.resolutionDateTime = ticket.getResolutionDateTime();
        this.hoursUntilAssigned = calculateDuration(creationDateTime, assignedDateTime);
        this.hoursFromAssignedToSolved = calculateDuration(assignedDateTime, resolutionDateTime);
        this.hoursUntilSolved = calculateDuration(creationDateTime, resolutionDateTime);
    }

    /**
     * Calculates the duration in hours between two LocalDateTime instances.
     *
     * @param start the starting LocalDateTime
     * @param end   the ending LocalDateTime
     * @return the duration in hours between start and end, or null if either parameter is null
     */
    private Long calculateDuration(LocalDateTime start, LocalDateTime end) {
        if (start != null && end != null) return Duration.between(start, end).toHours();
        return null;
    }
}
