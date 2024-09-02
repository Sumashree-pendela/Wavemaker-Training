package com.wavemaker.leave_management.repository.Impl;

import com.wavemaker.leave_management.dto.Employee;
import com.wavemaker.leave_management.exception.EmployeeNotFoundException;
import com.wavemaker.leave_management.exception.LoginNotFoundException;
import com.wavemaker.leave_management.repository.EmployeeRepository;
import com.wavemaker.leave_management.util.DatabaseConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepositoryImpl implements EmployeeRepository {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeRepositoryImpl.class);
    private static final String GET_EMPLOYEE = "SELECT * FROM EMPLOYEE WHERE USER_NAME = ?";
    private static final String GET_EMPLOYEE_BY_ID = "SELECT * FROM EMPLOYEE WHERE ID = ?";
    private static final String GET_EMPLOYEES_BY_MANAGER_ID = "SELECT * FROM EMPLOYEE WHERE MANAGER_ID = ?";

    private static EmployeeRepositoryImpl instance;

    private EmployeeRepositoryImpl() {
    }

    public static synchronized EmployeeRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new EmployeeRepositoryImpl();
        }
        return instance;
    }

    @Override
    public Employee getByUserName(String userName) throws LoginNotFoundException {
        logger.debug("Getting Employee Details by userName:");

        Employee employee = new Employee();
        try {
            PreparedStatement preparedStatement = DatabaseConnector.connect().prepareStatement(GET_EMPLOYEE);
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                employee.setId(resultSet.getInt(1));
                employee.setName(resultSet.getString(2));
                employee.setEmail(resultSet.getString(3));
                employee.setDob(resultSet.getDate(4).toLocalDate());
                employee.setPhoneNumber(resultSet.getString(5));
                employee.setGender(resultSet.getString(6));
                employee.setUserName(resultSet.getString(7));
                employee.setManagerId(resultSet.getInt(8));
            }
        } catch (SQLException e) {
            throw new LoginNotFoundException("No user found");
        }
        return employee;
    }

    @Override
    public Employee getById(int userId) throws LoginNotFoundException {
        logger.debug("Getting Employee Details by userId:");

        Employee employee = new Employee();
        try {
            PreparedStatement preparedStatement = DatabaseConnector.connect().prepareStatement(GET_EMPLOYEE_BY_ID);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                employee.setId(resultSet.getInt(1));
                employee.setName(resultSet.getString(2));
                employee.setEmail(resultSet.getString(3));
                employee.setDob(resultSet.getDate(4).toLocalDate());
                employee.setPhoneNumber(resultSet.getString(5));
                employee.setGender(resultSet.getString(6));
                employee.setUserName(resultSet.getString(7));
                employee.setManagerId(resultSet.getInt(8));
            }
        } catch (SQLException e) {
            throw new LoginNotFoundException("No user found");
        }
        return employee;
    }

    @Override
    public List<Employee> getEmployeesByManagerId(int userId) throws EmployeeNotFoundException {
        logger.debug("Getting Employee Details by userId: {}", userId);

        List<Employee> employeeList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = DatabaseConnector.connect().prepareStatement(GET_EMPLOYEES_BY_MANAGER_ID);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Employee employee = new Employee();

                employee.setId(resultSet.getInt(1));
                employee.setName(resultSet.getString(2));
                employee.setEmail(resultSet.getString(3));
                employee.setDob(resultSet.getDate(4).toLocalDate());
                employee.setPhoneNumber(resultSet.getString(5));
                employee.setGender(resultSet.getString(6));
                employee.setUserName(resultSet.getString(7));
                employee.setManagerId(resultSet.getInt(8));

                employeeList.add(employee);
            }
        } catch (SQLException e) {
            throw new EmployeeNotFoundException("No user found");
        }
        return employeeList;
    }
}
