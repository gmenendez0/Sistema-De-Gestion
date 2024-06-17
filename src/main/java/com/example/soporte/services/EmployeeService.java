package com.example.soporte.services;

import com.example.soporte.models.ExternalEntities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@org.springframework.stereotype.Service
public class EmployeeService extends  Service <Employee,Long>{
    @Autowired
    private RestTemplate restTemplate;
    public EmployeeService(JpaRepository<Employee, Long> repository) {
        super(repository);
    }
    private static final String API_URL = "https://anypoint.mulesoft.com/mocking/api/v1/sources/exchange/assets/754f50e8-20d8-4223-bbdc-56d50131d0ae/recursos-psa/1.0.0/m/api/recursos";

    public List<Employee> getEmployees() {
        Employee[] employeeArray = restTemplate.getForObject(API_URL, Employee[].class);
        return Arrays.asList(employeeArray);
    }
}
