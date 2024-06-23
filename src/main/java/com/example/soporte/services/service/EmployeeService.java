package com.example.soporte.services.service;

import com.example.soporte.externalAPI.EmployeeRestAPIController;
import com.example.soporte.models.ExternalEntities.Employee;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class EmployeeService extends Service{
    private final EmployeeRestAPIController employeeRestAPIController;

    public EmployeeService(){
        this.employeeRestAPIController = new EmployeeRestAPIController();
    }

    public List<Employee> getEmployees() {
        Employee[] employeeArray = employeeRestAPIController.getEmployees();
        return Optional.ofNullable(employeeArray).map(Arrays::asList).orElse(null);
    }

    public Employee getEmployeeByFileName(Long fileName) {
        if (fileName == null) return null;
        return getEmployees().stream().filter(employee -> employee.getFileName() == (fileName)).findFirst().orElse(null);
    }

    public boolean employeeExists(Long fileName) {
        return getEmployeeByFileName(fileName) != null;
    }
}
