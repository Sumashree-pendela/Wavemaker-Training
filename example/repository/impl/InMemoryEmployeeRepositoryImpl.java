package org.example.repository.impl;

import org.example.dto.EmployeeRequest;
import org.example.exception.EmployeeNotFoundException;
import org.example.model.EmployeeEntity;
import org.example.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InMemoryEmployeeRepositoryImpl implements EmployeeRepository {
    private static final Logger logger = LoggerFactory.getLogger(InMemoryEmployeeRepositoryImpl.class);

    private static InMemoryEmployeeRepositoryImpl instance;
    private final Map<Integer, EmployeeEntity> employeeMap;

    private InMemoryEmployeeRepositoryImpl() {
        employeeMap = new HashMap<>();
        System.out.println("InMemoryEmployeeRepository instance created");
    }

    //syncronized
    public static InMemoryEmployeeRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new InMemoryEmployeeRepositoryImpl();
        }
        return instance;
    }

    @Override
    public EmployeeEntity getByEmployeeId(int empId) throws EmployeeNotFoundException {
        logger.debug("Getting employee by id {}", empId);
        EmployeeEntity employeeEntity = employeeMap.get(empId);
        if (employeeEntity != null) {
            return employeeEntity;
        } else {
            throw new EmployeeNotFoundException("Employee with Id: " + empId + " is not found");
        }
    }

    @Override
    public void addEmployee(EmployeeRequest employeeRequest) throws IOException {
        logger.debug("Adding employee by with details {}", employeeRequest);

        int maxId = 0;
        Map<Integer, EmployeeEntity> employees = getAllEmployees();

        if (employees != null && !employees.isEmpty()) {
            for (Integer id : employees.keySet()) {
                if (id > maxId) {
                    maxId = id;
                }
            }
        }

        int employeeId = maxId + 1;
        EmployeeEntity employeeEntity = new EmployeeEntity(employeeId, employeeRequest.getName(), employeeRequest.getEmail(), employeeRequest.getAddressId());

        employeeMap.put(employeeEntity.getId(), employeeEntity);
        System.out.println("Employee added successfully");

        logger.debug("Employee Added successfully {} ", employeeMap);
    }

    @Override
    public Map<Integer, EmployeeEntity> getAllEmployees() {
        logger.debug("Getting all employees ");

        return employeeMap;
    }

    @Override
    public String updateEmployee(int id, EmployeeRequest employeeRequest) throws EmployeeNotFoundException {
        logger.debug("Updating employee by with details {}", employeeRequest);

        if (employeeMap.containsKey(id)) {
            EmployeeEntity emp = employeeMap.get(id);
            emp.setId(id);
            emp.setName(employeeRequest.getName());
            logger.debug("Employee updated successfully");
            return "Employee updated";
        } else {
            throw new EmployeeNotFoundException("Employee with Id: " + id + " doesn't exist");
        }
    }

    @Override
    public void deleteEmployee(int id) throws EmployeeNotFoundException {
        logger.debug("Deleting employee by with id {}", id);

        if (employeeMap.containsKey(id)) {
            employeeMap.remove(id);
            logger.debug("Employee with Id: {} deleted successfully", id);
        } else {
            throw new EmployeeNotFoundException("Employee with Id: " + id + " doesn't exist");
        }
    }
}
