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

    /**
     * Retrieves a list of all employees from the external API.
     *
     * @return a list of Employee objects retrieved from the external API, or null if no employees are found
     */
    public List<Employee> getEmployees() {
        Employee[] employeeArray = employeeRestAPIController.getEmployees();
        return Optional.ofNullable(employeeArray).map(Arrays::asList).orElse(null);
    }

    /**
     * Retrieves an employee by their file name.
     *
     * @param fileName the file name of the employee to retrieve
     * @return the Employee object with the specified file name, or null if the employee is not found or fileName is null
     */
    public Employee getEmployeeByFileName(Long fileName) {
        if (fileName == null) return null;
        return getEmployees().stream().filter(employee -> employee.getFileName() == (fileName)).findFirst().orElse(null);
    }

    /**
     * Checks if an employee with the specified file name does not exist.
     *
     * @param fileName the file name of the employee to check
     * @return true if an employee with the specified file name does not exist, false otherwise
     */
    public boolean employeeDoesNotExist(Long fileName) {
        return getEmployeeByFileName(fileName) == null;
    }
}
