package com.example.soporte.services;

import com.example.soporte.exceptions.InterfaceException;
import com.example.soporte.models.ExternalEntities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

//TODO:
//1. Quitar la tabla de la entidad Employee.
@org.springframework.stereotype.Service
public class EmployeeService extends  Service <Employee,Long>{
    private final RestTemplate restTemplate;

    private static final String API_URL = "https://anypoint.mulesoft.com/mocking/api/v1/sources/exchange/assets/754f50e8-20d8-4223-bbdc-56d50131d0ae/recursos-psa/1.0.0/m/api/recursos";

    public EmployeeService(){
        super(null);
        this.restTemplate = new RestTemplate();
    }

    public List<Employee> getEmployees() {
        try {
            Employee[] employeeArray = restTemplate.getForObject(API_URL, Employee[].class);
            return Optional.ofNullable(employeeArray).map(Arrays::asList).orElse(null);
        } catch(Exception e) {
            throw new InterfaceException("Error fetching employees from external API: " + e.getMessage());
        }
    }

    public Employee getEmployeeByFileName(Long fileName) {
        return getEmployees().stream().filter(employee -> employee.getFileName() == (fileName)).findFirst().orElse(null);
    }

    public boolean employeeExists(Long fileName) {
        return getEmployeeByFileName(fileName) != null;
    }
}
