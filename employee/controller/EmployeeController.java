package main.java.com.employee.controller;

import main.java.com.employee.dto.EmployeeRequest;
import main.java.com.employee.exception.AddressNotFoundException;
import main.java.com.employee.exception.EmployeeNotFoundException;
import main.java.com.employee.model.Employee;
import main.java.com.employee.service.EmployeeServiceImpl;

import java.io.IOException;
import java.util.List;

public class EmployeeController {

    EmployeeServiceImpl employeeService = new EmployeeServiceImpl();

    public void add(EmployeeRequest employeeRequest, boolean flag) throws IOException {
        employeeService.addEmployee(employeeRequest, flag);
    }

    public Employee getById(int id, boolean flag) throws EmployeeNotFoundException, AddressNotFoundException {
        return employeeService.getByEmployeeId(id, flag);
    }

    public String update(int id, EmployeeRequest employeeRequest, boolean flag) throws AddressNotFoundException, EmployeeNotFoundException {
        return employeeService.updateEmployee(id, employeeRequest, flag);
    }

    public List<Employee> getAll(boolean flag) throws EmployeeNotFoundException, AddressNotFoundException {
        return employeeService.getAllEmployees(flag);
    }

    public void delete(int id, boolean flag) throws EmployeeNotFoundException, AddressNotFoundException {
        employeeService.deleteEmployee(id, flag);
    }
}
