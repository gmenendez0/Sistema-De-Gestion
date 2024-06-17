package com.example.soporte.controllers;



import com.example.soporte.models.ExternalEntities.Client;
import com.example.soporte.models.ExternalEntities.Employee;
import com.example.soporte.services.ClientService;
import com.example.soporte.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/Employee")
public class EmployeeController extends Controller{

    private final EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<?> getEmployees(){
        try {
            Collection<Employee> employees = employeeService.getEmployees();
            return okResponse(employees);
        } catch (Exception e) {
            return handleError(e);
        }
    }

}