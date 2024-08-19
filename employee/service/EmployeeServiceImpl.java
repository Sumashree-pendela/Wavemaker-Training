package main.java.com.employee.service;

import main.java.com.employee.dto.EmployeeRequest;
import main.java.com.employee.exception.AddressNotFoundException;
import main.java.com.employee.exception.EmployeeNotFoundException;
import main.java.com.employee.model.Address;
import main.java.com.employee.model.Employee;
import main.java.com.employee.model.EmployeeEntity;
import main.java.com.employee.repository.EmployeeRepository;
import main.java.com.employee.repository.EmployeeRepositoryFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmployeeServiceImpl implements EmployeeService {

    EmployeeRepository repository = null;

    AddressServiceImpl addressService = new AddressServiceImpl();


    @Override
    public Employee getByEmployeeId(int empId, boolean flag) throws EmployeeNotFoundException, AddressNotFoundException {
        repository = EmployeeRepositoryFactory.getRepository(flag);
        EmployeeEntity employeeEntity = repository.getByEmployeeId(empId);
        Address address = null;

        if(employeeEntity.getAddressId() != 0) {
             address = addressService.getByAddressId(employeeEntity.getAddressId(), flag);
            System.out.println("Address.." + address);
        }
        Employee employee = new Employee(employeeEntity.getId(),employeeEntity.getName(), employeeEntity.getAddressId(), address);
        System.out.println(employee);
        return employee;
    }

    @Override
    public void addEmployee(EmployeeRequest employeeRequest, boolean flag) throws IOException {
        repository = EmployeeRepositoryFactory.getRepository(flag);

        Address address = new Address(employeeRequest.getAddressId(), employeeRequest.getLocation(), employeeRequest.getPin());
        addressService.addAddress(address, flag);

        repository.addEmployee(employeeRequest);
    }

    @Override
    public List<Employee> getAllEmployees(boolean flag) throws EmployeeNotFoundException, AddressNotFoundException {
        repository = EmployeeRepositoryFactory.getRepository(flag);

        Map<Integer, EmployeeEntity> employeeEntityMap = repository.getAllEmployees();
        List<Employee> employeeList = new ArrayList<>();
        for (EmployeeEntity employeeEntity:employeeEntityMap.values()) {
            Address address = addressService.getByAddressId(employeeEntity.getAddressId(), flag);
            Employee employee = new Employee(employeeEntity.getId(),employeeEntity.getName(), employeeEntity.getAddressId(), address);

            employeeList.add(employee);
        }
        return employeeList;
    }

    @Override
    public String updateEmployee(int id, EmployeeRequest employeeRequest, boolean flag) throws EmployeeNotFoundException, AddressNotFoundException {
        repository = EmployeeRepositoryFactory.getRepository(flag);
        Address address = new Address(employeeRequest.getAddressId(),employeeRequest.getLocation(), employeeRequest.getPin());
        addressService.updateAddress(employeeRequest.getAddressId(), address, flag);

        return repository.updateEmployee(id, employeeRequest);
    }

    @Override
    public void deleteEmployee(int id, boolean flag) throws EmployeeNotFoundException, AddressNotFoundException {
        repository = EmployeeRepositoryFactory.getRepository(flag);

        Employee employee = getByEmployeeId(id, flag);

        addressService.deleteAddress(employee.getAddressId(), flag);
        repository.deleteEmployee(id);
    }
}
