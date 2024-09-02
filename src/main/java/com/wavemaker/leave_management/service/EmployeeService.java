package com.wavemaker.leave_management.service;

import com.wavemaker.leave_management.dto.Employee;
import com.wavemaker.leave_management.exception.EmployeeNotFoundException;
import com.wavemaker.leave_management.exception.LoginNotFoundException;

import java.util.List;

public interface EmployeeService {
    Employee getByUserName(String userName) throws LoginNotFoundException;

    Employee getById(int userId) throws LoginNotFoundException;

    List<Employee> getEmployeeByUserId(int userId) throws EmployeeNotFoundException;
}
