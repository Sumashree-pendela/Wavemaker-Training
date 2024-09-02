package com.wavemaker.leave_management.service.Impl;

import com.wavemaker.leave_management.dto.Employee;
import com.wavemaker.leave_management.exception.EmployeeNotFoundException;
import com.wavemaker.leave_management.exception.LoginNotFoundException;
import com.wavemaker.leave_management.repository.EmployeeRepository;
import com.wavemaker.leave_management.repository.Impl.EmployeeRepositoryImpl;
import com.wavemaker.leave_management.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    EmployeeRepository employeeRepository = EmployeeRepositoryImpl.getInstance();

    @Override
    public Employee getByUserName(String userName) throws LoginNotFoundException {
        logger.debug("Getting Employee details by userName: {}", userName);
        return employeeRepository.getByUserName(userName);
    }

    @Override
    public Employee getById(int userId) throws LoginNotFoundException {
        logger.debug("Getting Employee Details by user id: {}", userId);
        return employeeRepository.getById(userId);
    }

    @Override
    public List<Employee> getEmployeeByUserId(int userId) throws EmployeeNotFoundException {
        logger.debug("Getting Employee Details under this manager Id: {}", userId);
        return employeeRepository.getEmployeesByManagerId(userId);
    }
}
