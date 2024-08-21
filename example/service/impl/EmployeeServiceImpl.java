package org.example.service.impl;

import org.example.dto.AddressRequest;
import org.example.dto.EmployeeRequest;
import org.example.enums.StorageType;
import org.example.exception.AddressNotFoundException;
import org.example.exception.EmployeeNotFoundException;
import org.example.model.Address;
import org.example.model.Employee;
import org.example.model.EmployeeEntity;
import org.example.repository.EmployeeRepository;
import org.example.repository.EmployeeRepositoryFactory;
import org.example.service.AddressService;
import org.example.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    EmployeeRepository repository = null;

    AddressService addressService = null;

    public EmployeeServiceImpl(StorageType storageType) {
        repository = EmployeeRepositoryFactory.getRepository(storageType);
       addressService =  new AddressServiceImpl(storageType);
    }

    public EmployeeServiceImpl() {
    }

    @Override
    public Employee getByEmployeeId(int empId) throws EmployeeNotFoundException, AddressNotFoundException {

        EmployeeEntity employeeEntity = repository.getByEmployeeId(empId);
        Address address = null;

        if (employeeEntity.getAddressId() != 0) {
            address = addressService.getByAddressId(employeeEntity.getAddressId());
            System.out.println("Address.." + address);
        }
        Employee employee = new Employee(employeeEntity.getId(), employeeEntity.getName(), employeeEntity.getEmail(), employeeEntity.getAddressId(), address);
        logger.debug("Details of employee {}", employee);
        return employee;
    }

    @Override
    public void addEmployee(EmployeeRequest employeeRequest) throws IOException, AddressNotFoundException, EmployeeNotFoundException {
        logger.debug("Adding Employee with details {} ", employeeRequest);

        AddressRequest addressRequest = new AddressRequest(employeeRequest.getLocation(), employeeRequest.getPin());
        System.out.println(addressRequest);
        int addressId = addressService.addAddress(addressRequest);

        employeeRequest.setAddressId(addressId);
        repository.addEmployee(employeeRequest);
    }

    @Override
    public List<Employee> getAllEmployees() throws EmployeeNotFoundException, AddressNotFoundException {
        logger.debug("Retrieving all employees");

        Map<Integer, EmployeeEntity> employeeEntityMap = repository.getAllEmployees();
        List<Employee> employeeList = new ArrayList<>();
        for (EmployeeEntity employeeEntity : employeeEntityMap.values()) {
            Address address = addressService.getByAddressId(employeeEntity.getAddressId());
            Employee employee = new Employee(employeeEntity.getId(), employeeEntity.getName(), employeeEntity.getEmail(), employeeEntity.getAddressId(), address);

            employeeList.add(employee);
        }
        return employeeList;
    }

    @Override
    public String updateEmployee(int id, EmployeeRequest employeeRequest) throws EmployeeNotFoundException, AddressNotFoundException {
        Employee employee = getByEmployeeId(id);
        logger.debug("Updating employee with {} with {}", id, employeeRequest);
        AddressRequest address = new AddressRequest(employeeRequest.getLocation(), employeeRequest.getPin());
        addressService.updateAddress(employee.getAddressId(), address);

        return repository.updateEmployee(id, employeeRequest);
    }

    @Override
    public void deleteEmployee(int id) throws EmployeeNotFoundException, AddressNotFoundException {
        logger.debug("Deleting employee with id {}", id);

        Employee employee = getByEmployeeId(id);

        repository.deleteEmployee(id);

        addressService.deleteAddress(employee.getAddressId());
    }
}
