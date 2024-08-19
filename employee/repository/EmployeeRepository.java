package main.java.com.employee.repository;

import main.java.com.employee.dto.EmployeeRequest;
import main.java.com.employee.exception.EmployeeNotFoundException;
import main.java.com.employee.model.Employee;
import main.java.com.employee.model.EmployeeEntity;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface EmployeeRepository {

    EmployeeEntity getByEmployeeId(int empId) throws EmployeeNotFoundException;
    void addEmployee(EmployeeRequest employeeRequest) throws IOException;
    Map<Integer, EmployeeEntity> getAllEmployees() throws EmployeeNotFoundException;
    String updateEmployee(int id, EmployeeRequest employeeRequest) throws EmployeeNotFoundException;
    void deleteEmployee(int id) throws EmployeeNotFoundException;
}
