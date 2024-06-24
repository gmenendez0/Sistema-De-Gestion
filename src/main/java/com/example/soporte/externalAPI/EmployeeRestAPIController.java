package com.example.soporte.externalAPI;

import com.example.soporte.exceptions.InterfaceException;
import com.example.soporte.models.ExternalEntities.Employee;

public class EmployeeRestAPIController extends RestAPIController{
    public EmployeeRestAPIController(){
        super("https://anypoint.mulesoft.com/mocking/api/v1/sources/exchange/assets/754f50e8-20d8-4223-bbdc-56d50131d0ae/recursos-psa/1.0.0/m/api/recursos");
    }

    /**
     * @inheritDoc
     */
    @Override
    protected String apiName(){
        return "EmployeeAPI";
    }

    /**
     * Retrieves an array of Employee objects from the API.
     *
     * @return an array of Employee objects retrieved from the API
     * @throws InterfaceException if an error occurs while fetching data from the API
     */
    public Employee[] getEmployees() throws InterfaceException{
        return getObject(Employee[].class);
    }
}
