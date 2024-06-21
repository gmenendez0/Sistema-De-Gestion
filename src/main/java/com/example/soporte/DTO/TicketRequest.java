package com.example.soporte.DTO;

import java.lang.reflect.Array;
import java.util.List;

public class TicketRequest {
    private String title;
    private String description;
    private String severity;
    private String status;
    private Long versionId;
    private List<Long> tasksId;

    public Long getVersionId() {
        return versionId;
    }

    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    public List<Long> getTasksId() {
        return tasksId;
    }

    public void setTasksId(List<Long> tasksId) {
        this.tasksId = tasksId;
    }

    private Long clientId;
    private Long employeeId;

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

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
}