package main.java.com.employee.repository;

import main.java.com.employee.dto.EmployeeRequest;
import main.java.com.employee.exception.EmployeeNotFoundException;
import main.java.com.employee.model.EmployeeEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InMemoryEmployeeRepository implements EmployeeRepository {

    private static InMemoryEmployeeRepository instance;
    private Map<Integer, EmployeeEntity> employeeMap;

    private InMemoryEmployeeRepository() {
        employeeMap = new HashMap<>();
        System.out.println("InMemoryEmployeeRepository instance created");
    }

    public static InMemoryEmployeeRepository getInstance() {
        if (instance == null) {
            instance = new InMemoryEmployeeRepository();
        }
        return instance;
    }

    @Override
    public EmployeeEntity getByEmployeeId(int empId) throws EmployeeNotFoundException {
        EmployeeEntity employeeEntity = employeeMap.get(empId);
        if (employeeEntity != null) {
            return employeeEntity;
        } else {
            throw new EmployeeNotFoundException("Employee with Id: " + empId + " is not found");
        }
    }

    @Override
    public void addEmployee(EmployeeRequest employeeRequest) throws IOException {
        EmployeeEntity employeeEntity = new EmployeeEntity(employeeRequest.getId(), employeeRequest.getName(), employeeRequest.getAddressId());

        employeeMap.put(employeeEntity.getId(), employeeEntity);

        System.out.println("Employee Added successfully");
        System.out.println(employeeMap);
    }

    @Override
    public Map<Integer, EmployeeEntity> getAllEmployees() throws EmployeeNotFoundException {
        if (!employeeMap.isEmpty()) {
            return employeeMap;
        } else {
            throw new EmployeeNotFoundException("There are no employees to return");
        }
    }

    @Override
    public String updateEmployee(int id, EmployeeRequest employeeRequest) throws EmployeeNotFoundException {
        if (employeeMap.containsKey(id)) {
            EmployeeEntity emp = employeeMap.get(id);
            emp.setId(employeeRequest.getId());
            emp.setName(employeeRequest.getName());
            return "Employee updated";
        } else {
            throw new EmployeeNotFoundException("Employee with Id: " + id + " doesn't exist");
        }
    }

    @Override
    public void deleteEmployee(int id) throws EmployeeNotFoundException {
        if (employeeMap.containsKey(id)) {
            employeeMap.remove(id);
            System.out.println("Employee with Id: " + id + " deleted successfully");
        } else {
            throw new EmployeeNotFoundException("Employee with Id: " + id + " doesn't exist");
        }
    }
}
