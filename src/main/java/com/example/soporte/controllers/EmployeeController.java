package com.example.soporte.controllers;

import com.example.soporte.models.ExternalEntities.Employee;
import com.example.soporte.services.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            return okResponse(employeeService.getEmployees());
        } catch (Exception e) {
            return handleError(e);
        }
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<?> getEmployeeByFileName(@PathVariable Long fileName){
        try {
            Employee employee = employeeService.getEmployeeByFileName(fileName);

            validateResource(employee);

            return okResponse(employeeService.getEmployeeByFileName(fileName));
        } catch (Exception e) {
            return handleError(e);
        }
    }
}