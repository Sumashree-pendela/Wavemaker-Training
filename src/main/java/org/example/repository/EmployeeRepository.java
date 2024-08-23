package org.example.repository;


import org.example.dto.EmployeeRequest;
import org.example.exception.EmployeeNotFoundException;
import org.example.model.EmployeeEntity;

import java.io.IOException;
import java.util.Map;

public interface EmployeeRepository {

    EmployeeEntity getByEmployeeId(int empId) throws EmployeeNotFoundException;
    void addEmployee(EmployeeRequest employeeRequest) throws IOException, EmployeeNotFoundException;
    Map<Integer, EmployeeEntity> getAllEmployees() throws EmployeeNotFoundException;
    String updateEmployee(int id, EmployeeRequest employeeRequest) throws EmployeeNotFoundException;
    void deleteEmployee(int id) throws EmployeeNotFoundException;
}
