package main.java.com.employee.service;

import main.java.com.employee.dto.EmployeeRequest;
import main.java.com.employee.exception.AddressNotFoundException;
import main.java.com.employee.exception.EmployeeNotFoundException;
import main.java.com.employee.model.Employee;

import java.io.IOException;
import java.util.List;

public interface EmployeeService {

    Employee getByEmployeeId(int empId, boolean flag) throws EmployeeNotFoundException, AddressNotFoundException;
    void addEmployee(EmployeeRequest employeeRequest, boolean flag) throws IOException;
    List<Employee> getAllEmployees(boolean flag) throws EmployeeNotFoundException, AddressNotFoundException;
    String updateEmployee(int id, EmployeeRequest employeeRequest,  boolean flag) throws EmployeeNotFoundException, AddressNotFoundException;
    void deleteEmployee(int id, boolean flag) throws EmployeeNotFoundException, AddressNotFoundException;
}
