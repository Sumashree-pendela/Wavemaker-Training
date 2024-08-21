package org.example.repository.impl;

import org.example.dto.EmployeeRequest;
import org.example.exception.EmployeeNotFoundException;
import org.example.model.EmployeeEntity;
import org.example.repository.EmployeeRepository;
import org.example.util.DatabaseConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DatabaseEmployeeRepositoryImpl implements EmployeeRepository {


    private static final Logger logger = LoggerFactory.getLogger(DatabaseEmployeeRepositoryImpl.class);

    private static DatabaseEmployeeRepositoryImpl instance;

    private DatabaseEmployeeRepositoryImpl() {
        System.out.println("Database Employee instance created");
    }

    public static DatabaseEmployeeRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new DatabaseEmployeeRepositoryImpl();
        }
        return instance;
    }


    @Override
    public EmployeeEntity getByEmployeeId(int empId) throws EmployeeNotFoundException {
        String selectQuery = "select * from employee where id = ?";
        EmployeeEntity employee = new EmployeeEntity();
        try {
            PreparedStatement preparedStatement = DatabaseConnector.connect().prepareStatement(selectQuery);
            preparedStatement.setInt(1, empId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                employee.setId(empId);
                employee.setName(resultSet.getString(2));
                employee.setEmail(resultSet.getString(3));
                employee.setAddressId(resultSet.getInt(4));
                return employee;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addEmployee(EmployeeRequest employeeRequest) throws IOException, EmployeeNotFoundException {
        logger.debug("Adding employee into database with details {} ", employeeRequest);
        String query = "insert into employee(name, email, address_id) values(?,?,?)";
        try {
            PreparedStatement preparedStatement = DatabaseConnector.connect().prepareStatement(query);
            preparedStatement.setString(1, employeeRequest.getName());
            preparedStatement.setString(2, employeeRequest.getEmail());
            preparedStatement.setInt(3, employeeRequest.getAddressId());
            preparedStatement.executeUpdate();
            logger.debug("Inserted into employee");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Map<Integer, EmployeeEntity> getAllEmployees() throws EmployeeNotFoundException {
        logger.debug("Getting all employees");
        String query = "select * from employee";

        Map<Integer, EmployeeEntity> employeeEntityMap = new HashMap<>();

        try {
            PreparedStatement preparedStatement = DatabaseConnector.connect().prepareStatement(query);
            ResultSet resultset = preparedStatement.executeQuery(query);
            System.out.println(resultset);
            while (resultset.next()) {
                EmployeeEntity employee = new EmployeeEntity();
                employee.setId(resultset.getInt(1));
                employee.setName(resultset.getString(2));
                employee.setEmail(resultset.getString(3));
                employee.setAddressId(resultset.getInt(4));

                employeeEntityMap.put(resultset.getInt(1), employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeEntityMap;
    }

    @Override
    public String updateEmployee(int id, EmployeeRequest employeeRequest) throws EmployeeNotFoundException {
        logger.debug("Updating employee with details {}", employeeRequest);
        String query = "update employee set name = ?, email = ? where id = ?";
        try {
            PreparedStatement preparedStatement = DatabaseConnector.connect().prepareStatement(query);
            preparedStatement.setString(1, employeeRequest.getLocation());
            preparedStatement.setInt(2, employeeRequest.getPin());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Updated successfully";
    }

    @Override
    public void deleteEmployee(int id) throws EmployeeNotFoundException {
        logger.debug("Deleting employee with id {}", id);

        String deleteQuery = "delete from employee where id = ?";
        try {
            PreparedStatement preparedStatement = DatabaseConnector.connect().prepareStatement(deleteQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Deleted address successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
