package com.example.soporte.DTO;

import com.example.soporte.models.ExternalEntities.Client;
import com.example.soporte.models.ExternalEntities.Employee;
import com.example.soporte.models.ExternalEntities.Task;
import com.example.soporte.models.Product.Version;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class CreateTicketDTO{
    @NotNull(message = "title can not be null.")
    @Size(min = 2, max = 100, message = "title must be at least 2 and max 100 characters.")
    public String title;

    @Size(max = 300, message = "description max size is 300 characters.")
    public String description;

    @NotBlank(message = "severity can not be blank.")
    public String severity;

    @NotBlank(message = "status can not be blank.")
    public String status;

    @NotNull(message = "versionId can not be null.")
    public Long versionId;

    @NotNull(message = "clientId can not be null.")
    public Long clientId;

    public Long employeeId;

    @Size(max = 20, message = "Can not relate more than 20 tasks at once.")
    public List<Long> tasksIds = new ArrayList<>();

    //Campos que no se espera que vengan en la request:
    public Employee employee;
    public Client client;
    public Version version;
    public List<Task> tasks = new ArrayList<>();

    //Validaciones adicionales que no deben hacerse hasta que se hayan asignado los valores a los campos que no se esperan en la request.
    public void validatePostRequestFields(){
        String error = "";

        if(client == null) error += "Client can not be null. ";
        if(version == null) error += "Version can not be null. ";

        if(!error.isEmpty()) throw new IllegalArgumentException(error);
    }
}