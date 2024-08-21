package org.example.repository;


import org.example.dto.AddressRequest;
import org.example.exception.AddressNotFoundException;
import org.example.model.Address;

import java.io.IOException;
import java.util.Map;

public interface AddressRepository {
    Address getById(int addrId) throws AddressNotFoundException;

    int addAddress(AddressRequest address) throws IOException, AddressNotFoundException;

    Map<Integer, Address> getAllAddresses() throws AddressNotFoundException;

    String updateAddress(int id, AddressRequest address) throws AddressNotFoundException;

    void deleteAddress(int id) throws AddressNotFoundException;
}
