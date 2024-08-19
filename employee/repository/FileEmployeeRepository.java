package main.java.com.employee.repository;

import main.java.com.employee.dto.EmployeeRequest;
import main.java.com.employee.exception.EmployeeNotFoundException;
import main.java.com.employee.model.EmployeeEntity;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileEmployeeRepository implements EmployeeRepository {

    String FILE_PATH = "C:\\Users\\sumashreep_700058\\Desktop\\empData.txt";

    private static FileEmployeeRepository instance;

    private FileEmployeeRepository() {
        System.out.println("FileEmployeeRepository instance created");
    }

    public static FileEmployeeRepository getInstance() {
        if (instance == null) {
            instance = new FileEmployeeRepository();
        }
        return instance;
    }

    @Override
    public EmployeeEntity getByEmployeeId(int empId) throws EmployeeNotFoundException {

        Map<Integer, EmployeeEntity> employeeEntityMap = getAllEmployees();
        for (EmployeeEntity employee : employeeEntityMap.values()) {
            if (employee.getId() == empId) {
                return employee;
            }
        }
        return null;
    }

    @Override
    public void addEmployee(EmployeeRequest employeeRequest) throws IOException {

        try (FileWriter fileWriter = new FileWriter(FILE_PATH, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            String employeeData = employeeRequest.getId() + ", " + employeeRequest.getName() + ", " +
                    employeeRequest.getAddressId();

            bufferedWriter.write(employeeData);
            System.out.println(employeeData);
            bufferedWriter.newLine();
            System.out.println("Data successfully inserted into file");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<Integer, EmployeeEntity> getAllEmployees() throws EmployeeNotFoundException {
        Map<Integer, EmployeeEntity> employeeEntityMap = new HashMap<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(", ");

                EmployeeEntity employee = new EmployeeEntity(Integer.parseInt(data[0]), data[1], Integer.parseInt(data[2]));
                employeeEntityMap.put(employee.getId(), employee);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return employeeEntityMap;
    }


    @Override
    public String updateEmployee(int id, EmployeeRequest employeeRequest) throws EmployeeNotFoundException {
        Map<Integer, EmployeeEntity> employeeMap = getAllEmployees();
        EmployeeEntity employee = employeeMap.get(id);

        if (employee != null) {
            employee.setName(employeeRequest.getName());
            employee.setAddressId(employeeRequest.getAddressId());
            System.out.println("Updated successfully");
            saveAllEmployees(employeeMap);
            return "updated";
        } else {
            throw new EmployeeNotFoundException("Employee with Id: " + id + " doesn't exist");
        }
    }


    @Override
    public void deleteEmployee(int id) throws EmployeeNotFoundException {
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
                String employeeData = employee.getId() + ", " + employee.getName() + ", " +
                        employee.getAddressId();

                bufferedWriter.write(employeeData);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
