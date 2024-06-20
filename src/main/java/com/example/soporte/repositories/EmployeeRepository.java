package com.example.soporte.repositories;

import com.example.soporte.models.ExternalEntities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {

}
