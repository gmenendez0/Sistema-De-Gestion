package com.example.soporte.externalAPI;

import com.example.soporte.models.ExternalEntities.Employee;

public class EmployeeRestAPIController extends RestAPIController{
    public EmployeeRestAPIController(){
        super("https://anypoint.mulesoft.com/mocking/api/v1/sources/exchange/assets/754f50e8-20d8-4223-bbdc-56d50131d0ae/recursos-psa/1.0.0/m/api/recursos");
    }

    @Override
    protected String apiName(){
        return "EmployeeAPI";
    }
    
    public Employee[] getEmployees(){
        return getObject(Employee[].class);
    }
}
