package main.java.com.employee.service;

import main.java.com.employee.exception.AddressNotFoundException;
import main.java.com.employee.model.Address;
import main.java.com.employee.model.Employee;

import java.io.IOException;
import java.util.List;

public interface AddressService {
    Address getByAddressId(int addrId, boolean flag) throws AddressNotFoundException;
    void addAddress(Address address,  boolean flag) throws IOException;
    List<Address> getAllAddresses(boolean flag) throws AddressNotFoundException;
    String updateAddress(int id, Address address,  boolean flag) throws AddressNotFoundException;
    void deleteAddress(int id, boolean flag) throws AddressNotFoundException;
}
