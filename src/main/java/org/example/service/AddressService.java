package org.example.service;


import org.example.dto.AddressRequest;
import org.example.exception.AddressNotFoundException;
import org.example.model.Address;

import java.io.IOException;
import java.util.List;

public interface AddressService {
    Address getByAddressId(int addrId) throws AddressNotFoundException;
    int addAddress(AddressRequest address) throws IOException, AddressNotFoundException;
    List<Address> getAllAddresses() throws AddressNotFoundException;
    String updateAddress(int id, AddressRequest address) throws AddressNotFoundException;
    void deleteAddress(int id) throws AddressNotFoundException;
}
