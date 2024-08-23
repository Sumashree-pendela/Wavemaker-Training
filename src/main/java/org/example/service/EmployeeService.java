package org.example.service;


import org.example.dto.EmployeeRequest;
import org.example.exception.AddressNotFoundException;
import org.example.exception.EmployeeNotFoundException;
import org.example.model.Employee;

import java.io.IOException;
import java.util.List;

public interface EmployeeService {

    Employee getByEmployeeId(int empId) throws EmployeeNotFoundException, AddressNotFoundException;
    void addEmployee(EmployeeRequest employeeRequest) throws IOException, AddressNotFoundException, EmployeeNotFoundException;
    List<Employee> getAllEmployees() throws EmployeeNotFoundException, AddressNotFoundException;
    String updateEmployee(int id, EmployeeRequest employeeRequest) throws EmployeeNotFoundException, AddressNotFoundException;
    void deleteEmployee(int id) throws EmployeeNotFoundException, AddressNotFoundException;
}
