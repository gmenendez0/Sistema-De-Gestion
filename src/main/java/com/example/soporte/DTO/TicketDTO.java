package com.example.soporte.DTO;

import java.util.List;

public class TicketDTO{
    public String title;

    public String description;

    public String severity;

    public String status;

    public Long versionId;

    public List<Long> tasksIds;

    public Long clientId;

    public Long employeeId;
}