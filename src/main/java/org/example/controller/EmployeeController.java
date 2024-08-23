package org.example.controller;

import org.example.dto.EmployeeRequest;
import org.example.enums.StorageType;
import org.example.exception.AddressNotFoundException;
import org.example.exception.EmployeeNotFoundException;
import org.example.model.Employee;
import org.example.service.EmployeeService;
import org.example.service.impl.EmployeeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    private EmployeeService employeeService = null;

    public EmployeeController(StorageType storageType) {
        employeeService = new EmployeeServiceImpl(storageType);
    }

    public void add(EmployeeRequest employeeRequest) throws IOException, AddressNotFoundException, EmployeeNotFoundException {
        logger.debug("Adding employee with details {}", employeeRequest);
        employeeService.addEmployee(employeeRequest);
    }

    public Employee getById(int id) throws EmployeeNotFoundException, AddressNotFoundException {
        logger.debug("Getting employee with id: {}", id);
        return employeeService.getByEmployeeId(id);
    }

    public String update(int id, EmployeeRequest employeeRequest) throws AddressNotFoundException, EmployeeNotFoundException {
        logger.debug("Updating employee {} with details {}", id, employeeRequest);
        return employeeService.updateEmployee(id, employeeRequest);
    }

    public List<Employee> getAll() throws EmployeeNotFoundException, AddressNotFoundException {
        logger.debug("Retrieving all employees");
        return employeeService.getAllEmployees();
    }

    public void delete(int id) throws EmployeeNotFoundException, AddressNotFoundException {
        logger.debug("Deleting employee with id: {}", id);
        employeeService.deleteEmployee(id);
    }
}
