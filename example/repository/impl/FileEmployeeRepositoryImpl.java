package org.example.repository.impl;

import org.example.dto.EmployeeRequest;
import org.example.exception.EmployeeNotFoundException;
import org.example.model.EmployeeEntity;
import org.example.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileEmployeeRepositoryImpl implements EmployeeRepository {
    private static final Logger logger = LoggerFactory.getLogger(FileEmployeeRepositoryImpl.class);


    String FILE_PATH = "C:\\Users\\sumashreep_700058\\Desktop\\empData.txt";

    private static FileEmployeeRepositoryImpl instance;

    private FileEmployeeRepositoryImpl() {
        System.out.println("FileEmployeeRepository instance created");
    }

    public static FileEmployeeRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new FileEmployeeRepositoryImpl();
        }
        return instance;
    }

    @Override
    public EmployeeEntity getByEmployeeId(int empId) throws EmployeeNotFoundException {
        logger.debug("Getting employee from file , where id {}", empId);

        Map<Integer, EmployeeEntity> employeeEntityMap = getAllEmployees();
        EmployeeEntity employee = employeeEntityMap.get(empId);
        if (employee != null) {
            return employee;
        } else {
            throw new EmployeeNotFoundException("Employee with Id: " + empId + " is not found");
        }
    }

    @Override
    public void addEmployee(EmployeeRequest employeeRequest) throws IOException {
        logger.debug("Adding Employee into file with details {} ", employeeRequest);

        try (FileWriter fileWriter = new FileWriter(FILE_PATH, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

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
            String employeeData = employeeId + ", " + employeeRequest.getName() + ", " + employeeRequest.getEmail() + ", " + employeeRequest.getAddressId();

            bufferedWriter.write(employeeData);
            System.out.println(employeeData);
            bufferedWriter.newLine();
            logger.debug("Data successfully inserted into file");

        } catch (IOException | EmployeeNotFoundException e) {
            logger.debug(e.toString());
        }
    }

    @Override
    public Map<Integer, EmployeeEntity> getAllEmployees() throws EmployeeNotFoundException {
        logger.debug("Retrieving all employees from file");

        Map<Integer, EmployeeEntity> employeeEntityMap = new HashMap<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(", ");

                EmployeeEntity employee = new EmployeeEntity(Integer.parseInt(data[0]), data[1], data[2], Integer.parseInt(data[3]));
                employeeEntityMap.put(employee.getId(), employee);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return employeeEntityMap;
    }


    @Override
    public String updateEmployee(int id, EmployeeRequest employeeRequest) throws EmployeeNotFoundException {
        logger.debug("Updating employee with {} with {}", id, employeeRequest);

        Map<Integer, EmployeeEntity> employeeMap = getAllEmployees();
        EmployeeEntity employee = employeeMap.get(id);

        if (employee != null) {
            employee.setName(employeeRequest.getName());
            System.out.println("Updated successfully");
            saveAllEmployees(employeeMap);
            return "updated";
        } else {
            throw new EmployeeNotFoundException("Employee with Id: " + id + " doesn't exist");
        }
    }


    @Override
    public void deleteEmployee(int id) throws EmployeeNotFoundException {
        logger.debug("Deleting employee with id from file{}", id);

        Map<Integer, EmployeeEntity> employeeMap = getAllEmployees();
        if (employeeMap.containsKey(id)) {
            employeeMap.remove(id);
            System.out.println("Employee Deleted successfully");
            saveAllEmployees(employeeMap);
        } else {
            throw new EmployeeNotFoundException("Employee with Id: " + id + " doesn't exist");
        }
    }

    private void saveAllEmployees(Map<Integer, EmployeeEntity> employeeEntityMap) {
        try (FileWriter fileWriter = new FileWriter(FILE_PATH, false);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            for (EmployeeEntity employee : employeeEntityMap.values()) {
                String employeeData = employee.getId() + ", " + employee.getName() + ", " + employee.getEmail();

                bufferedWriter.write(employeeData);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
