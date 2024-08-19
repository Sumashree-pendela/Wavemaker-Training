package main.java.com.employee.repository;

import main.java.com.employee.exception.AddressNotFoundException;
import main.java.com.employee.model.Address;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface AddressRepository {
    Address getById(int addrId) throws AddressNotFoundException;

    void addAddress(Address address) throws IOException;

    Map<Integer, Address> getAllAddresses() throws AddressNotFoundException;

    String updateAddress(int id, Address address) throws AddressNotFoundException;

    void deleteAddress(int id) throws AddressNotFoundException;
}
